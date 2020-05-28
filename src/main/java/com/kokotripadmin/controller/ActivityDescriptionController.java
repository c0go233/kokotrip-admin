package com.kokotripadmin.controller;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionImageDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionInfoDto;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.city.CityImageDto;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.city.CityImageNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.interfaces.activity.ActivityDescriptionService;
import com.kokotripadmin.service.interfaces.activity.ActivityService;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.activity.ActivityDescriptionInfoVm;
import com.kokotripadmin.viewmodel.activity.ActivityDescriptionVm;
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
@RequestMapping("activity/description")
public class ActivityDescriptionController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityDescriptionService activityDescriptionService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private Convert convert;

    private final String ACTIVITY_DESCRIPTION_VM = "activityDescriptionVm";


    @GetMapping("/add")
    public String addActivityDescription(Model model, @RequestParam("activityId") Integer activityId)
    throws ActivityNotFoundException {


        if (!model.containsAttribute(ACTIVITY_DESCRIPTION_VM)) {
            ActivityDto activityDto = activityService.findById(activityId);
            ActivityDescriptionVm tourSpotDescriptionVm = new ActivityDescriptionVm(true,
                                                                                    activityDto.getTourSpotId(),
                                                                                    activityDto.getTourSpotName(),
                                                                                    activityDto.getId(),
                                                                                    activityDto.getName());
            model.addAttribute(ACTIVITY_DESCRIPTION_VM, tourSpotDescriptionVm);
        }
        return "activity/description/activity-description-form";
    }

    @GetMapping("/edit/{id}")
    public String editActivityDescription(Model model, @PathVariable("id") Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException {

        ActivityDescriptionDto activityDescriptionDto = activityDescriptionService.findById(activityDescriptionId);
        ActivityDescriptionVm activityDescriptionVm = modelMapper
                .map(activityDescriptionDto, ActivityDescriptionVm.class);
        model.addAttribute(ACTIVITY_DESCRIPTION_VM, activityDescriptionVm);
        return "activity/description/activity-description-form";
    }

    @GetMapping("/detail/{id}")
    public String detailActivityDescription(Model model, @PathVariable("id") Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException {

        ActivityDescriptionDto activityDescriptionDto = activityDescriptionService
                .findByIdInDetail(activityDescriptionId);
        ActivityDescriptionVm activityDescriptionViewModel = modelMapper.map(activityDescriptionDto,
                                                                             ActivityDescriptionVm.class);

        model.addAttribute(ACTIVITY_DESCRIPTION_VM, activityDescriptionViewModel);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "activity/description/activity-description-detail-page";
    }

    @PostMapping("/save")
    public String saveActivityDescription(
            @Valid @ModelAttribute("activityDescriptionVm") ActivityDescriptionVm activityDescriptionVm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) throws ActivityNotFoundException {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityDescriptionVm, "");
            return "redirect:/activity/description/add";
        }

        try {
            ActivityDescriptionDto activityDescriptionDto = modelMapper
                    .map(activityDescriptionVm, ActivityDescriptionDto.class);
            Integer activityDescriptionId = activityDescriptionService.save(activityDescriptionDto);
            return "redirect:/activity/description/detail/" + activityDescriptionId;
        } catch (ActivityDescriptionAlreadyExistsException | ActivityDescriptionInfoAlreadyExistsException |
                ActivityInfoNotFoundException | ActivityDescriptionNotFoundException | SupportLanguageNotFoundException |
                ActivityDescriptionInfoNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityDescriptionVm, exception.getMessage());
            return "redirect:/activity/description/add";
        }
    }

//  ==================================== IMAGE ==========================================  //

    @PostMapping(value = "/image/save",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveActivityDescriptionImage(@RequestParam("image") MultipartFile multipartFile,
                                                               @RequestParam("fileName") String fileName,
                                                               @RequestParam("activityDescriptionId") Integer activityDescriptionId,
                                                               @RequestParam("order") Integer order,
                                                               @RequestParam("repImage") boolean repImage) {
        try {
            ActivityDescriptionImageDto activityDescriptionImageDto = new ActivityDescriptionImageDto(fileName,
                                                                                                      multipartFile.getContentType(),
                                                                                                      order,
                                                                                                      activityDescriptionId,
                                                                                                      multipartFile);
            Integer activityDescriptionImageId = activityDescriptionService.saveImage(activityDescriptionImageDto);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(convert.resultToJson(activityDescriptionImageId.toString()));
        } catch (AmazonServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityDescriptionNotFoundException | FileIsNotImageException | ImageDuplicateException | IOException |
                SdkClientException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/image/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteActivityDescriptionImage(@RequestParam("id") Integer imageId) {

        try {
            activityDescriptionService.deleteImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(imageId.toString()));
        } catch (AmazonServiceException | ActivityDescriptionImageNotFoundException | RepImageNotDeletableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        } catch (SdkClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }


    @PostMapping(value = "/image/order/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveActivityDescriptionImageOrder(@RequestBody List<Integer> imageIdList) {
        activityDescriptionService.updateImageOrder(imageIdList);
        return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(""));
    }


//  ================================================ INFO ==============================================================  //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveActivityDescriptionInfo(
            @Valid @ModelAttribute ActivityDescriptionInfoVm activityDescriptionInfoVm,
            BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            ActivityDescriptionInfoDto activityDescriptionInfoDto = modelMapper.map(activityDescriptionInfoVm,
                                                                                    ActivityDescriptionInfoDto.class);
            activityDescriptionInfoDto = activityDescriptionService.saveInfo(activityDescriptionInfoDto);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(objectMapper.writeValueAsString(activityDescriptionInfoDto));

        } catch (ActivityDescriptionInfoNotEditableException | ActivityDescriptionInfoAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityDescriptionNotFoundException | ActivityInfoNotFoundException | ActivityDescriptionInfoNotFoundException |
                SupportLanguageNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteActivityDescription(@RequestParam("id") Integer activityDescriptionId) {
        try {
            Integer activityId = activityDescriptionService.delete(activityDescriptionId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/activity/detail/" + activityId;
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티설명은 삭제할수없습니다."));
        } catch (ActivityDescriptionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteActivityDescriptionInfo(@RequestParam("id") Integer activityDescriptionInfoId) {

        try {
            activityDescriptionService.deleteInfo(activityDescriptionInfoId);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(convert.resultToJson(activityDescriptionInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티설명 번역정보는 삭제할수없습니다."));
        } catch (ActivityDescriptionInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityDescriptionInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       ActivityDescriptionVm activityDescriptionVm,
                                       String exceptionMessage) throws ActivityNotFoundException {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        ActivityDto activityDto = activityService.findById(activityDescriptionVm.getActivityId());
        activityDescriptionVm.setActivityName(activityDto.getName());
        activityDescriptionVm.setTourSpotId(activityDto.getTourSpotId());
        activityDescriptionVm.setTourSpotName(activityDto.getTourSpotName());
        redirectAttributes.addFlashAttribute(ACTIVITY_DESCRIPTION_VM, activityDescriptionVm);
        redirectAttributes.addAttribute("activityId", activityDescriptionVm.getActivityId());
    }
}
