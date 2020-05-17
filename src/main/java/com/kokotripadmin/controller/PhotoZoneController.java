package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.photozone.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;
import com.kokotripadmin.service.interfaces.PhotoZoneService;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.activity.ActivityService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.photozone.PhotoZoneInfoVm;
import com.kokotripadmin.viewmodel.photozone.PhotoZoneVm;
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
@RequestMapping("/photo-zone")
public class PhotoZoneController extends BaseController {

    @Autowired
    private PhotoZoneService photoZoneService;

    @Autowired
    private TourSpotService tourSpotService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Convert convert;

    @Autowired
    private ObjectMapper objectMapper;

    private final String PHOTO_ZONE_VM = "photoZoneVm";

    @GetMapping("/add")
    public String addPhotoZone(Model model, @RequestParam("parentTourSpotId") Integer parentTourSpotId)
    throws TourSpotNotFoundException, ActivityNotFoundException {

        PhotoZoneVm photoZoneVm = model.containsAttribute(PHOTO_ZONE_VM) ?
                                  (PhotoZoneVm) model.asMap().get(PHOTO_ZONE_VM) :
                                  new PhotoZoneVm(true, parentTourSpotId);

        photoZoneVm.setParentTourSpotName(tourSpotService.getNameById(parentTourSpotId));

        if (photoZoneVm.getTourSpotId() != null)
            photoZoneVm.setTourSpotName(tourSpotService.getNameById(photoZoneVm.getTourSpotId()));

        if (photoZoneVm.getActivityId() != null)
            photoZoneVm.setActivityName(activityService.getNameById(photoZoneVm.getActivityId()));

        model.addAttribute(PHOTO_ZONE_VM, photoZoneVm);

        return "photo-zone/photo-zone-form";
    }

    @GetMapping("/edit/{id}")
    public String editPhotoZone(Model model, @PathVariable("id") Integer photoZoneId)
    throws PhotoZoneNotFoundException {
        PhotoZoneDto photoZoneDto = photoZoneService.findById(photoZoneId);
        PhotoZoneVm photoZoneVm = modelMapper.map(photoZoneDto, PhotoZoneVm.class);
        model.addAttribute(PHOTO_ZONE_VM, photoZoneVm);
        return "photo-zone/photo-zone-form";
    }

    @GetMapping("/detail/{id}")
    public String detailPhotoZone(@PathVariable("id") Integer photoZoneId, Model model) throws PhotoZoneNotFoundException {
        PhotoZoneDto photoZoneDto = photoZoneService.findByIdInDetail(photoZoneId);
        PhotoZoneVm photoZoneVm = modelMapper.map(photoZoneDto, PhotoZoneVm.class);
        model.addAttribute(PHOTO_ZONE_VM, photoZoneVm);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "photo-zone/photo-zone-detail-page";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deletePhotoZone(@RequestParam("id") Integer photoZoneId) {
        try {
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tour-spot/detail/" + photoZoneService.delete(photoZoneId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 포토존은 삭제할수없습니다."));
        } catch (PhotoZoneNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping("/save")
    public String savePhotoZone(@Valid @ModelAttribute("photoZoneVm") PhotoZoneVm photoZoneVm,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, photoZoneVm, "");
            redirectAttributes.addAttribute("parentTourSpotId", photoZoneVm.getParentTourSpotId());
            return "redirect:/photo-zone/add";
        }

        try {
            PhotoZoneDto photoZoneDto = modelMapper.map(photoZoneVm, PhotoZoneDto.class);
            Integer photoZoneId = photoZoneService.save(photoZoneDto);
            return "redirect:/photo-zone/detail/" + photoZoneId;
        } catch (PhotoZoneInfoAlreadyExistsException | SupportLanguageNotFoundException | PhotoZoneInfoNotFoundException |
                TourSpotNotFoundException | TourSpotInfoNotFoundException | PhotoZoneNotFoundException |
                PhotoZoneTourSpotDuplicateException | ActivityInfoNotFoundException | PhotoZoneDuplicateException |
                ActivityNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, photoZoneVm, exception.getMessage());
            redirectAttributes.addAttribute("parentTourSpotId", photoZoneVm.getParentTourSpotId());
            return "redirect:/photo-zone/add";
        }
    }

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> savePhotoZoneInfo(@ModelAttribute @Valid PhotoZoneInfoVm photoZoneInfoVm,
                                                    BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            PhotoZoneInfoDto photoZoneInfoDto = modelMapper.map(photoZoneInfoVm, PhotoZoneInfoDto.class);
            photoZoneInfoDto = photoZoneService.saveInfo(photoZoneInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(photoZoneInfoDto));
        } catch (SupportLanguageNotFoundException | PhotoZoneNotFoundException | PhotoZoneInfoNotFoundException |
                TourSpotInfoNotFoundException | ActivityInfoNotFoundException | TagInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (PhotoZoneInfoAlreadyExistsException | PhotoZoneInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deletePhotoZoneInfo(@RequestParam("id") Integer photoZoneInfoId) {

        try {
            photoZoneService.deleteInfo(photoZoneInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(photoZoneInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 포토존 번역정보는 삭제할수없습니다."));
        } catch (PhotoZoneInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (PhotoZoneInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       PhotoZoneVm photoZoneVm,
                                       String exceptionMessage) {
        setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(PHOTO_ZONE_VM, photoZoneVm);
    }


}
