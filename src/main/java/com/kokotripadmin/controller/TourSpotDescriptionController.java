package com.kokotripadmin.controller;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.city.CityImageDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionInfoDto;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionInfo;
import com.kokotripadmin.exception.city.CityImageNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotDescriptionService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotDescriptionInfoVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotDescriptionVm;
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
@RequestMapping("tour-spot/description")
public class TourSpotDescriptionController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TourSpotDescriptionService tourSpotDescriptionService;

    @Autowired
    private TourSpotService tourSpotService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private Convert convert;

    private final String TOUR_SPOT_DESCRIPTION_VM = "tourSpotDescriptionVm";


    @GetMapping("/add")
    public String addTourSpotDescription(Model model, @RequestParam("tourSpotId") Integer tourSpotId)
    throws TourSpotNotFoundException {

        if (!model.containsAttribute(TOUR_SPOT_DESCRIPTION_VM)) {
            String tourSpotName = tourSpotService.getNameById(tourSpotId);
            TourSpotDescriptionVm tourSpotDescriptionVm = new TourSpotDescriptionVm(tourSpotId, tourSpotName, true);
            model.addAttribute(TOUR_SPOT_DESCRIPTION_VM, tourSpotDescriptionVm);
        }
        return "tour-spot/description/tour-spot-description-form";
    }


    @GetMapping("/edit/{id}")
    public String editTourSpotDescription(Model model, @PathVariable("id") Integer tourSpotDescriptionId)
    throws TourSpotDescriptionNotFoundException {

        TourSpotDescriptionDto tourSpotDescriptionDto = tourSpotDescriptionService.findById(tourSpotDescriptionId);
        TourSpotDescriptionVm tourSpotDescriptionVm = modelMapper.map(tourSpotDescriptionDto, TourSpotDescriptionVm.class);
        model.addAttribute(TOUR_SPOT_DESCRIPTION_VM, tourSpotDescriptionVm);
        return "tour-spot/description/tour-spot-description-form";
    }

    @GetMapping("/detail/{id}")
    public String detailTourSpotDescription(Model model, @PathVariable("id") Integer tourSpotDescriptionId)
    throws TourSpotDescriptionNotFoundException {

        TourSpotDescriptionDto tourSpotDescriptionDto = tourSpotDescriptionService.findByIdInDetail(tourSpotDescriptionId);
        TourSpotDescriptionVm tourSpotDescriptionViewModel = modelMapper.map(tourSpotDescriptionDto,
                                                                             TourSpotDescriptionVm.class);

        model.addAttribute(TOUR_SPOT_DESCRIPTION_VM, tourSpotDescriptionViewModel);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "tour-spot/description/tour-spot-description-detail-page";
    }

    @PostMapping("/save")
    public String saveTourSpotDescription(@Valid @ModelAttribute("tourSpotDescriptionVm") TourSpotDescriptionVm tourSpotDescriptionVm,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) throws TourSpotNotFoundException {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, tourSpotDescriptionVm, "");
            return "redirect:/tour-spot/description/add";
        }

        try {
            TourSpotDescriptionDto tourSpotDescriptionDto = modelMapper.map(tourSpotDescriptionVm, TourSpotDescriptionDto.class);
            Integer tourSpotDescriptionId = tourSpotDescriptionService.save(tourSpotDescriptionDto);
            return "redirect:/tour-spot/description/detail/" + tourSpotDescriptionId;
        } catch (TourSpotDescriptionAlreadyExistsException | TourSpotDescriptionInfoAlreadyExistsException |
                TourSpotInfoNotFoundException | TourSpotDescriptionNotFoundException | SupportLanguageNotFoundException |
                TourSpotDescriptionInfoNotFoundException exception) {

            setRedirectAttributes(redirectAttributes, bindingResult, tourSpotDescriptionVm, exception.getMessage());
            return "redirect:/tour-spot/description/add";
        }
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTourSpotDescription(@RequestParam("id") Integer tourSpotDescriptionId) {
        try {
            Integer tourSpotId = tourSpotDescriptionService.delete(tourSpotDescriptionId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tour-spot/detail/" + tourSpotId;
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지설명은 삭제할수없습니다."));
        } catch (TourSpotDescriptionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


//  ==================================== CITY IMAGE ==========================================  //

    @PostMapping(value = "/image/save",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveTourSpotDescriptionImage(@RequestParam("image") MultipartFile multipartFile,
                                                @RequestParam("fileName") String fileName,
                                                @RequestParam("tourSpotDescriptionId") Integer tourSpotDescriptionId,
                                                @RequestParam("order") Integer order,
                                                @RequestParam("repImage") boolean repImage) {
        try {
            TourSpotDescriptionImageDto tourSpotDescriptionImageDto = new TourSpotDescriptionImageDto(fileName,
                                                                                                      multipartFile.getContentType(),
                                                                                                      order,
                                                                                                      tourSpotDescriptionId,
                                                                                                      multipartFile);
            Integer tourSpotDescriptionImageId = tourSpotDescriptionService.saveImage(tourSpotDescriptionImageDto);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(tourSpotDescriptionImageId.toString()));
        } catch (AmazonServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotDescriptionNotFoundException | FileIsNotImageException | ImageDuplicateException | IOException |
                SdkClientException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/image/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteTourSpotDescriptionImage(@RequestParam("id") Integer imageId) {

        try {
            tourSpotDescriptionService.deleteImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(imageId.toString()));
        } catch (AmazonServiceException | TourSpotDescriptionImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        } catch (SdkClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }


    @PostMapping(value = "/image/order/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveTourSpotDescriptionImageOrder(@RequestBody List<Integer> imageIdList) {
        tourSpotDescriptionService.updateImageOrder(imageIdList);
        return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(""));
    }

//  ============================================= INFO ============================================================  //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveTourSpotDescriptionInfo(@Valid @ModelAttribute TourSpotDescriptionInfoVm tourSpotDescriptionInfoVm,
                                                              BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {

            TourSpotDescriptionInfoDto tourSpotDescriptionInfoDto = modelMapper.map(tourSpotDescriptionInfoVm,
                                                                                    TourSpotDescriptionInfoDto.class);

            tourSpotDescriptionInfoDto = tourSpotDescriptionService.saveInfo(tourSpotDescriptionInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(tourSpotDescriptionInfoDto));

        } catch (TourSpotDescriptionInfoNotEditableException | TourSpotDescriptionInfoAlreadyExistsException exception) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));

        } catch (TourSpotDescriptionNotFoundException | TourSpotInfoNotFoundException | TourSpotDescriptionInfoNotFoundException |
                SupportLanguageNotFoundException exception) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteTourSpotDescriptionInfo(@RequestParam("id") Integer tourSpotDescriptionInfoId) {

        try {
            tourSpotDescriptionService.deleteInfo(tourSpotDescriptionInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(tourSpotDescriptionInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지설명 번역정보는 삭제할수없습니다."));
        } catch (TourSpotDescriptionInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotDescriptionInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       TourSpotDescriptionVm tourSpotDescriptionVm,
                                       String exceptionMessage) throws TourSpotNotFoundException {

        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        String tourSpotName = tourSpotService.getNameById(tourSpotDescriptionVm.getTourSpotId());
        tourSpotDescriptionVm.setTourSpotName(tourSpotName);
        redirectAttributes.addFlashAttribute(TOUR_SPOT_DESCRIPTION_VM, tourSpotDescriptionVm);
        redirectAttributes.addAttribute("tourSpotId", tourSpotDescriptionVm.getTourSpotId());
    }

}
