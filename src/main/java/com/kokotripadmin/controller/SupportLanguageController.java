package com.kokotripadmin.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.dto.common.SupportLanguageDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNameDuplicateException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.common.SupportLanguageVm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/support-language")
public class SupportLanguageController extends BaseController {


    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Convert convert;

    @Autowired
    private ObjectMapper objectMapper;

    private final String SUPPORT_LANGUAGE_VM   = "supportLanguageVm";
    private final String SUPPORT_LANGUAGE_LIST = "supportLanguageList";


    @GetMapping("/list")
    public String listSupportLanguage(Model model) {
        model.addAttribute(SUPPORT_LANGUAGE_LIST, supportLanguageService.findAll());
        return "support-language/support-language-list";
    }

    @GetMapping("/add")
    public String addSupportLanguage(Model model) {

        if (!model.containsAttribute(SUPPORT_LANGUAGE_VM))
            model.addAttribute(SUPPORT_LANGUAGE_VM, new SupportLanguageVm(true));

        return "support-language/support-language-form";
    }

    @PostMapping("/save")
    public String saveSupportLanguage(@Valid @ModelAttribute("supportLanguageVm") SupportLanguageVm supportLanguageVm,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, supportLanguageVm, "");
            return "redirect:/support-language/add";
        }

        try {
            SupportLanguageDto supportLanguageDto = modelMapper.map(supportLanguageVm, SupportLanguageDto.class);
            supportLanguageService.save(supportLanguageDto);
            return "redirect:/support-language/list";
        } catch (SupportLanguageNameDuplicateException | SupportLanguageNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, supportLanguageVm, exception.getMessage());
            return "redirect:/support-language/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editSupportLanguage(Model model, @PathVariable("id") Integer supportLanguageId)
    throws SupportLanguageNotFoundException {
        SupportLanguageDto supportLanguageDto = supportLanguageService.findById(supportLanguageId);
        model.addAttribute(SUPPORT_LANGUAGE_VM, modelMapper.map(supportLanguageDto, SupportLanguageVm.class));
        return "support-language/support-language-form";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteSupportLanguage(@RequestParam("id") Integer supportLanguageId) {
        try {
            supportLanguageService.delete(supportLanguageId);
            String json = convert.resultToJson(supportLanguageId.toString());
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(supportLanguageId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 번역언어는 삭제할수없습니다."));
        } catch (SupportLanguageNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       SupportLanguageVm supportLanguageVm,
                                       String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(SUPPORT_LANGUAGE_VM, supportLanguageVm);
    }
}
