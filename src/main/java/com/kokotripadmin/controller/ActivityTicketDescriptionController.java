package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.activity.ActivityTicketDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityTicketDescriptionInfoDto;
import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.activity.ActivityTicketDescriptionService;
import com.kokotripadmin.service.interfaces.activity.ActivityTicketService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.activity.ActivityTicketDescriptionInfoVm;
import com.kokotripadmin.viewmodel.activity.ActivityTicketDescriptionVm;
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
@RequestMapping("activity/ticket/description")
public class ActivityTicketDescriptionController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityTicketDescriptionService activityTicketDescriptionService;

    @Autowired
    private ActivityTicketService activityTicketService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private Convert convert;

    private final String ACTIVITY_TICKET_DESCRIPTION_VM = "activityTicketDescriptionVm";

    @GetMapping("/add")
    public String addActivityTicketDescription(Model model, @RequestParam("activityTicketId") Integer activityTicketId)
    throws ActivityTicketNotFoundException {

        if (!model.containsAttribute(ACTIVITY_TICKET_DESCRIPTION_VM)) {
            ActivityTicketDto activityTicketDto = activityTicketService.findById(activityTicketId);
            ActivityTicketDescriptionVm activityTicketDescriptionVm =
                    new ActivityTicketDescriptionVm(activityTicketDto.getTourSpotId(),
                                                    activityTicketDto.getTourSpotName(),
                                                    activityTicketDto.getActivityId(),
                                                    activityTicketDto.getActivityName(),
                                                    activityTicketDto.getId(),
                                                    activityTicketDto.getName(),
                                                    true);
            model.addAttribute(ACTIVITY_TICKET_DESCRIPTION_VM, activityTicketDescriptionVm);
        }
        return "activity/ticket/description/activity-ticket-description-form";
    }

    @GetMapping("/edit/{id}")
    public String editActivityTicketDescription(Model model, @PathVariable("id") Integer activityTicketDescriptionId)
    throws ActivityTicketDescriptionNotFoundException {

        ActivityTicketDescriptionDto activityTicketDescriptionDto =
                activityTicketDescriptionService.findById(activityTicketDescriptionId);
        ActivityTicketDescriptionVm activityTicketDescriptionVm =
                modelMapper.map(activityTicketDescriptionDto, ActivityTicketDescriptionVm.class);
        model.addAttribute(ACTIVITY_TICKET_DESCRIPTION_VM, activityTicketDescriptionVm);
        return "activity/ticket/description/activity-ticket-description-form";
    }


    @GetMapping("/detail/{id}")
    public String detailActivityTicketDescription(Model model, @PathVariable("id") Integer activityDescriptionId)
    throws ActivityTicketDescriptionNotFoundException {

        ActivityTicketDescriptionDto activityTicketDescriptionDto =
                activityTicketDescriptionService.findByIdInDetail(activityDescriptionId);
        ActivityTicketDescriptionVm activityTicketDescriptionVm =
                modelMapper.map(activityTicketDescriptionDto, ActivityTicketDescriptionVm.class);

        model.addAttribute(ACTIVITY_TICKET_DESCRIPTION_VM, activityTicketDescriptionVm);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "activity/ticket/description/activity-ticket-description-detail-page";
    }

    @PostMapping("/save")
    public String saveActivityTicketDescription(@Valid @ModelAttribute("activityTicketDescriptionVm")
                                                ActivityTicketDescriptionVm activityTicketDescriptionVm,
                                                BindingResult bindingResult,
                                                RedirectAttributes redirectAttributes)
    throws ActivityNotFoundException {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityTicketDescriptionVm, "");
            return "redirect:/activity/ticket/description/add";
        }

        try {
            ActivityTicketDescriptionDto activityTicketDescriptionDto =
                    modelMapper.map(activityTicketDescriptionVm, ActivityTicketDescriptionDto.class);
            Integer activityDescriptionId = activityTicketDescriptionService.save(activityTicketDescriptionDto);
            return "redirect:/activity/ticket/description/detail/" + activityDescriptionId;
        } catch (ActivityTicketDescriptionAlreadyExistsException | ActivityTicketDescriptionInfoAlreadyExistsException |
                ActivityTicketInfoNotFoundException | ActivityTicketDescriptionNotFoundException | SupportLanguageNotFoundException |
                ActivityTicketDescriptionInfoNotFoundException | ActivityTicketNotFoundException exception) {
            setRedirectAttributes(redirectAttributes,
                                  bindingResult,
                                  activityTicketDescriptionVm,
                                  exception.getMessage());
            return "redirect:/activity/ticket/description/add";
        }
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteActivityTicketDescription(@RequestParam("id") Integer activityTicketDescriptionId) {
        try {
            Integer activityTicketId = activityTicketDescriptionService.delete(activityTicketDescriptionId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/activity/ticket/detail/" + activityTicketId;
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티티켓설명은 삭제할수없습니다."));
        } catch (ActivityTicketDescriptionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveActivityTicketDescriptionInfo(@Valid @ModelAttribute
                                                                    ActivityTicketDescriptionInfoVm activityTicketDescriptionInfoVm,
                                                                    BindingResult bindingResult)
    throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));
        try {
            ActivityTicketDescriptionInfoDto activityTicketDescriptionInfoDto =
                    modelMapper.map(activityTicketDescriptionInfoVm, ActivityTicketDescriptionInfoDto.class);
            activityTicketDescriptionInfoDto = activityTicketDescriptionService.saveInfo(activityTicketDescriptionInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(activityTicketDescriptionInfoDto));
        } catch (ActivityTicketDescriptionInfoNotEditableException | ActivityTicketDescriptionInfoAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityTicketDescriptionNotFoundException | ActivityTicketInfoNotFoundException |
                ActivityTicketDescriptionInfoNotFoundException | SupportLanguageNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteActivityTicketDescriptionInfo(@RequestParam("id") Integer activityTicketDescriptionInfoId) {

        try {
            activityTicketDescriptionService.deleteInfo(activityTicketDescriptionInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(activityTicketDescriptionInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티티켓설명 번역정보는 삭제할수없습니다."));
        } catch (ActivityTicketDescriptionInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityTicketDescriptionInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       ActivityTicketDescriptionVm activityTicketDescriptionVm,
                                       String exceptionMessage) {

        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(ACTIVITY_TICKET_DESCRIPTION_VM, activityTicketDescriptionVm);
        redirectAttributes.addAttribute("activityTicketId", activityTicketDescriptionVm.getActivityTicketId());
    }
}
