package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dao.interfaces.tag.ThemeInfoDao;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.exception.city.*;
import com.kokotripadmin.exception.region.RegionNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagNameDuplicateException;
import com.kokotripadmin.exception.theme.*;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.ThemeService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.city.CityInfoVm;
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
@RequestMapping("/theme")
public class ThemeController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private Convert convert;

    private final String THEME_VM = "themeVm";

    @GetMapping("/list")
    public String listTheme() {
        return "theme/theme-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<ThemeDto> showThemeListPaginated(@Valid DataTablesInput dataTablesInput) {
        return themeService.findAllByPagination(dataTablesInput);
    }

    @GetMapping("/add")
    public String addTheme(Model model) {
        if (!model.containsAttribute(THEME_VM))
            model.addAttribute(THEME_VM, new ThemeVm(true));
        return "theme/theme-form";
    }

    @PostMapping("/save")
    public String saveTheme(@Valid @ModelAttribute("themeVm") ThemeVm themeVm,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, themeVm, "");
            return "redirect:/theme/add";
        }

        try {
            ThemeDto themeDto = modelMapper.map(themeVm, ThemeDto.class);
            return "redirect:/theme/detail/" + themeService.save(themeDto);
        } catch (ThemeNotFoundException | TagNameDuplicateException | SupportLanguageNotFoundException |
                ThemeNameDuplicateException | ThemeInfoAlreadyExistsException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, themeVm, exception.getMessage());
            return "redirect:/theme/add";
        }
    }

    @GetMapping("/detail/{id}")
    public String detailTheme(@PathVariable("id") Integer themeId, Model model) throws ThemeNotFoundException {
        ThemeDto themeDto = themeService.findByIdInDetail(themeId);
        model.addAttribute(THEME_VM, modelMapper.map(themeDto, ThemeVm.class));
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "theme/theme-detail-page";
    }

    @GetMapping("/edit/{id}")
    public String editTheme(Model model, @PathVariable("id") Integer themeId) throws ThemeNotFoundException {
        ThemeDto themeDto = themeService.findById(themeId);
        model.addAttribute(THEME_VM, modelMapper.map(themeDto, ThemeVm.class));
        return "theme/theme-form";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTheme(@RequestParam("id") Integer themeId) {
        try {
            themeService.delete(themeId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/theme/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 상위분류는 삭제할수없습니다."));
        } catch (ThemeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


//  ================================================ THEME INFO ================================================  //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveThemeInfo(@ModelAttribute @Valid ThemeInfoVm themeInfoVm,
                                               BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            ThemeInfoDto themeInfoDto = modelMapper.map(themeInfoVm, ThemeInfoDto.class);
            themeInfoDto = themeService.saveInfo(themeInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(themeInfoDto));
        } catch (SupportLanguageNotFoundException | ThemeNotFoundException | ThemeInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(e.getMessage()));
        } catch (ThemeInfoNotEditableException | ThemeInfoAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteCityInfo(@RequestParam("id") Integer themeInfoId) {

        try {
            themeService.deleteInfo(themeInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(themeInfoId.toString()));
        } catch (ThemeInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ThemeInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       ThemeVm themeVm,
                                       String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(THEME_VM, themeVm);
    }
}
