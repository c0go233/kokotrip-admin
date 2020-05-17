package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.dto.tag.TagInfoDto;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.*;
import com.kokotripadmin.exception.theme.*;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.TagService;
import com.kokotripadmin.service.interfaces.ThemeService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.tag.TagInfoVm;
import com.kokotripadmin.viewmodel.tag.TagVm;
import com.kokotripadmin.viewmodel.theme.ThemeInfoVm;
import com.kokotripadmin.viewmodel.theme.ThemeVm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tag")
public class TagController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private Convert convert;

    private final String TAG_VM = "tagVm";


    @GetMapping("/list")
    public String listTag() {
        return "tag/tag-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<TagDto> showTagListPaginated(@Valid DataTablesInput dataTablesInput) {
        return tagService.findAllByPagination(dataTablesInput);
    }

    @GetMapping("/add")
    public String addTag(Model model, @RequestParam(value = "themeId", required = false) Integer themeId)
    throws ThemeNotFoundException {

        String themeName = themeService.findNameById(themeId);
        TagVm tagVm = model.containsAttribute(TAG_VM) ? (TagVm) model.asMap().get(TAG_VM) : new TagVm();
        tagVm.setMetaData(themeId, themeName, true);

        model.addAttribute(TAG_VM, tagVm);
        model.addAttribute(AppConstant.THEME_LINKED_HASH_MAP, themeService.findAllAsLinkedHashMap());
        return "tag/tag-form";
    }

    @PostMapping("/save")
    public String saveTag(@Valid @ModelAttribute("tagVm") TagVm tagVm,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, tagVm, "");
            return "redirect:/tag/add";
        }

        try {
            TagDto tagDto = modelMapper.map(tagVm, TagDto.class);
            return "redirect:/tag/detail/" + tagService.save(tagDto);
        } catch (TagNotFoundException | SupportLanguageNotFoundException | TagNameDuplicateException |
                ThemeNotFoundException | TagInfoAlreadyExistsException | TagInfoNotFoundException e) {
            setRedirectAttributes(redirectAttributes, bindingResult, tagVm, e.getMessage());
            redirectAttributes.addAttribute("themeId", tagVm.getThemeId());
            return "redirect:/tag/add";
        }
    }

    @GetMapping("/detail/{id}")
    public String detailTag(@PathVariable("id") Integer tagId, Model model) throws TagNotFoundException {
        TagDto tagDto = tagService.findByIdInDetail(tagId);
        model.addAttribute(TAG_VM, modelMapper.map(tagDto, TagVm.class));
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "tag/tag-detail-page";
    }

    @GetMapping("/edit/{id}")
    public String editTag(Model model, @PathVariable("id") Integer tagId) throws TagNotFoundException {
        TagDto tagDto = tagService.findById(tagId);
        model.addAttribute(TAG_VM, modelMapper.map(tagDto, TagVm.class));
        model.addAttribute(AppConstant.THEME_LINKED_HASH_MAP, themeService.findAllAsLinkedHashMap());
        return "tag/tag-form";
    }


    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTag(@RequestParam("id") Integer tagId) {
        try {
            tagService.delete(tagId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tag/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 하위분류는 삭제할수없습니다."));
        } catch (TagNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


    //  ================================================ THEME INFO ================================================  //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveTagInfo(@ModelAttribute @Valid TagInfoVm tagInfoVm,
                                              BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            TagInfoDto tagInfoDto = modelMapper.map(tagInfoVm, TagInfoDto.class);
            tagInfoDto = tagService.saveInfo(tagInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(tagInfoDto));
        } catch (SupportLanguageNotFoundException | TagNotFoundException | TagInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(e.getMessage()));
        } catch (TagInfoNotEditableException | TagInfoAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteCityInfo(@RequestParam("id") Integer tagInfoId) {

        try {
            tagService.deleteInfo(tagInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(tagInfoId.toString()));
        } catch (TagInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TagInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       TagVm tagVm,
                                       String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(TAG_VM, tagVm);
    }
}
