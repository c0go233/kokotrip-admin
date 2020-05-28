package com.kokotripadmin.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.dto.activity.ActivityTicketImageDto;
import com.kokotripadmin.dto.activity.ActivityTicketInfoDto;
import com.kokotripadmin.dto.city.CityImageDto;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.city.CityImageNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.TicketTypeService;
import com.kokotripadmin.service.interfaces.activity.ActivityService;
import com.kokotripadmin.service.interfaces.activity.ActivityTicketService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.activity.ActivityTicketInfoVm;
import com.kokotripadmin.viewmodel.activity.ActivityTicketVm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("activity/ticket")
public class ActivityTicketController extends BaseController {

    @Autowired
    private ActivityTicketService activityTicketService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Convert convert;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ACTIVITY_TICKET_VM = "activityTicketVm";

    @GetMapping("/add")
    public String addActivityTicket(Model model, @RequestParam("activityId") Integer activityId)
    throws ActivityNotFoundException {

        if (!model.containsAttribute(ACTIVITY_TICKET_VM)) {
            ActivityDto activityDto = activityService.findById(activityId);
            model.addAttribute(ACTIVITY_TICKET_VM, new ActivityTicketVm(true,
                                                                        activityDto.getTourSpotId(),
                                                                        activityDto.getTourSpotName(),
                                                                        activityDto.getId(),
                                                                        activityDto.getName()));
        }
        model.addAttribute(AppConstant.TICKET_TYPE_LINKED_HASH_MAP, ticketTypeService.findAllAsLinkedHashMap());
        return "activity/ticket/activity-ticket-form";
    }

    @GetMapping("/edit/{id}")
    public String editActivityTicket(Model model, @PathVariable("id") Integer activityId)
    throws ActivityTicketNotFoundException {
        ActivityTicketDto activityTicketDto = activityTicketService.findById(activityId);
        ActivityTicketVm activityTicketVm = modelMapper.map(activityTicketDto, ActivityTicketVm.class);
        model.addAttribute(ACTIVITY_TICKET_VM, activityTicketVm);
        model.addAttribute(AppConstant.TICKET_TYPE_LINKED_HASH_MAP, ticketTypeService.findAllAsLinkedHashMap());
        return "activity/ticket/activity-ticket-form";
    }

    @GetMapping("/detail/{id}")
    public String detailActivityTicket(@PathVariable("id") Integer activityTicketId,
                                       Model model) throws ActivityTicketNotFoundException {
        ActivityTicketDto activityTicketDto = activityTicketService.findByIdInDetail(activityTicketId);
        ActivityTicketVm activityTicketVm = modelMapper.map(activityTicketDto, ActivityTicketVm.class);
        model.addAttribute(ACTIVITY_TICKET_VM, activityTicketVm);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());

        return "activity/ticket/activity-ticket-detail-page";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteActivityTicket(@RequestParam("id") Integer activityTicketId) {
        try {
            String returnUrl = "redirect:" + super.getBaseUrl() + "/activity/detail/" + activityTicketService
                    .delete(activityTicketId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티티켓은 삭제할수없습니다."));
        } catch (ActivityTicketNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping("/save")
    public String saveActivityTicket(@Valid @ModelAttribute("activityTicketVm") ActivityTicketVm activityTicketVm,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityTicketVm, "");
            redirectAttributes.addAttribute("activityId", activityTicketVm.getActivityId());
            return "redirect:/activity/ticket/add";
        }

        try {
            ActivityTicketDto activityTicketDto = modelMapper.map(activityTicketVm, ActivityTicketDto.class);
            Integer activityTicketId = activityTicketService.save(activityTicketDto);
            return "redirect:/activity/ticket/detail/" + activityTicketId;
        } catch (ActivityTicketInfoAlreadyExistsException | SupportLanguageNotFoundException | ActivityTicketInfoNotFoundException |
                ActivityNotFoundException | TicketTypeNotFoundException | ActivityInfoNotFoundException | ActivityTicketNotFoundException |
                ActivityTicketNameDuplicateException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityTicketVm, exception.getMessage());
            redirectAttributes.addAttribute("activityId", activityTicketVm.getActivityId());
            return "redirect:/activity/ticket/add";
        }
    }

//  ==================================== IMAGE ==========================================  //

    @PostMapping(value = "/image/save",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveActivityTicketImage(@RequestParam("image") MultipartFile multipartFile,
                                                          @RequestParam("fileName") String fileName,
                                                          @RequestParam("activityTicketId") Integer activityTicketId,
                                                          @RequestParam("order") Integer order,
                                                          @RequestParam("repImage") boolean repImage) {
        try {
            ActivityTicketImageDto activityTicketImageDto = new ActivityTicketImageDto(fileName,
                                                                                       multipartFile.getContentType(),
                                                                                       order,
                                                                                       repImage, activityTicketId,
                                                                                       multipartFile);
            Integer activityTicketImageId = activityTicketService.saveImage(activityTicketImageDto);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(activityTicketImageId.toString()));
        } catch (AmazonServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityTicketNotFoundException | FileIsNotImageException | ImageDuplicateException | IOException |
                SdkClientException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/image/rep-image/update", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> updateRepImage(@RequestParam("imageId") Integer imageId) {
        try {
            activityTicketService.updateRepImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(""));
        } catch (ActivityTicketImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(e.getMessage()));
        }
    }

    @PostMapping(value = "/image/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteActivityTicketImage(@RequestParam("id") Integer imageId) {

        try {
            activityTicketService.deleteImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(imageId.toString()));
        } catch (AmazonServiceException | ActivityTicketImageNotFoundException | RepImageNotDeletableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        } catch (SdkClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }


    @PostMapping(value = "/image/order/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveActivityTicketImageOrder(@RequestBody List<Integer> imageIdList) {
        activityTicketService.updateImageOrder(imageIdList);
        return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(""));
    }


//  ================================================= INFO ======================================================== //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveActivityTicketInfo(
            @ModelAttribute @Valid ActivityTicketInfoVm activityTicketInfoVm,
            BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            ActivityTicketInfoDto activityTicketInfoDto = modelMapper
                    .map(activityTicketInfoVm, ActivityTicketInfoDto.class);
            activityTicketInfoDto = activityTicketService.saveInfo(activityTicketInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(activityTicketInfoDto));
        } catch (SupportLanguageNotFoundException | ActivityTicketNotFoundException | ActivityTicketInfoNotFoundException |
                ActivityInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityTicketInfoAlreadyExistsException | ActivityTicketInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteActivityTicketInfo(@RequestParam("id") Integer activityTicketInfoId) {

        try {
            activityTicketService.deleteInfo(activityTicketInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(activityTicketInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티티켓 번역정보는 삭제할수없습니다."));
        } catch (ActivityTicketInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityTicketInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       ActivityTicketVm activityTicketVm,
                                       String exceptionMessage) {
        setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(ACTIVITY_TICKET_VM, activityTicketVm);
    }
}
