package com.kokotripadmin.controller;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.city.CityImageDto;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.city.*;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.CityService;
import com.kokotripadmin.service.interfaces.StateService;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.city.CityInfoVm;
import com.kokotripadmin.viewmodel.city.CityVm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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
@RequestMapping("/city")
public class CityController extends BaseController {


    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private Convert convert;

    private final String CITY_VM = "cityVm";

    @GetMapping("/list")
    public String showStateList() {
        return "city/city-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<CityDto> showCityListPaginated(@Valid DataTablesInput input) {
        return cityService.findAllByPagination(input);
    }

    @GetMapping("/add")
    public String addCity(Model model, @RequestParam(value = "stateId", required = false) Integer stateId)
    throws StateNotFoundException {

        String stateName = stateService.findNameById(stateId);
        if (!model.containsAttribute(CITY_VM))
            model.addAttribute(CITY_VM, new CityVm(stateName, stateId, true));
        else {
            CityVm cityVm = (CityVm) model.asMap().get(CITY_VM);
            cityVm.setStateName(stateName);
        }
        model.addAttribute(AppConstant.STATE_LINKED_HASH_MAP, stateService.findAllAsLinkedHashMap());
        return "city/city-form";
    }

    @GetMapping("/edit/{id}")
    public String editCity(Model model, @PathVariable("id") int cityId) throws CityNotFoundException {
        model.addAttribute(CITY_VM, modelMapper.map(cityService.findById(cityId), CityVm.class));
        model.addAttribute(AppConstant.STATE_LINKED_HASH_MAP, stateService.findAllAsLinkedHashMap());
        return "city/city-form";
    }

    @PostMapping("/save")
    public String saveCity(@Valid @ModelAttribute("cityVm") CityVm cityVm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setCityRedirectAttributes(redirectAttributes, bindingResult, cityVm, "");
            redirectAttributes.addAttribute("stateId", cityVm.getStateId());
            return "redirect:/city/add";
        }

        try {
            CityDto cityDto = modelMapper.map(cityVm, CityDto.class);
            return "redirect:/city/detail/" + cityService.save(cityDto);
        } catch (CityNameDuplicateException | StateNotFoundException | SupportLanguageNotFoundException |
                CityInfoAlreadyExistsException | CityNotFoundException exception) {
            setCityRedirectAttributes(redirectAttributes, bindingResult, cityVm, exception.getMessage());
            redirectAttributes.addAttribute("stateId", cityVm.getStateId());
            return "redirect:/city/add";
        }
    }

    @GetMapping("/detail/{id}")
    public String detailCity(Model model, @PathVariable("id") int cityId) throws CityNotFoundException {
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        model.addAttribute(CITY_VM, modelMapper.map(cityService.findByIdInDetail(cityId), CityVm.class));
        return "/city/city-detail-page";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteCity(@RequestParam("id") Integer cityId) {
        try {
            cityService.delete(cityId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/city/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 도시는 삭제할수없습니다."));
        } catch (CityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }



//  ==================================== CITY IMAGE ==========================================  //

    @PostMapping(value = "/image/save",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveCityImage(@RequestParam("image") MultipartFile multipartFile,
                                                @RequestParam("fileName") String fileName,
                                                @RequestParam("cityId") Integer cityId,
                                                @RequestParam("order") Integer order,
                                                @RequestParam("repImage") boolean repImage) {
        try {
            CityImageDto cityImageDto = new CityImageDto(fileName, multipartFile.getContentType(), order,
                                                         repImage, cityId, multipartFile);
            Integer cityImageId = cityService.saveImage(cityImageDto);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(cityImageId.toString()));
        } catch (AmazonServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (CityNotFoundException | FileIsNotImageException | ImageDuplicateException | IOException |
                SdkClientException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/image/rep-image/update", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> updateRepImage(@RequestParam("imageId") Integer imageId) {
        try {
            cityService.updateRepImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(""));
        } catch (CityImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(e.getMessage()));
        }
    }

    @PostMapping(value = "/image/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteCityImage(@RequestParam("id") Integer imageId) {

        try {
            cityService.deleteImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(imageId.toString()));
        } catch (AmazonServiceException | CityImageNotFoundException | RepImageNotDeletableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        } catch (SdkClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }


    @PostMapping(value = "/image/order/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveCityImageOrder(@RequestBody List<Integer> imageIdList) {
        cityService.updateImageOrder(imageIdList);
        return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(""));
    }



//  ==================================== CITY INFO ==========================================  //

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteCityInfo(@RequestParam("id") Integer cityInfoId) {

        try {
            cityService.deleteInfo(cityInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(cityInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 도시 번역정보는 삭제할수없습니다."));
        } catch (CityInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (CityInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveCityInfo(@ModelAttribute @Valid CityInfoVm cityInfoVm,
                                               BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            CityInfoDto cityInfoDto = modelMapper.map(cityInfoVm, CityInfoDto.class);
            cityInfoDto = cityService.saveInfo(cityInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(cityInfoDto));
        } catch (CityNotFoundException | SupportLanguageNotFoundException | CityInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (CityInfoAlreadyExistsException | CityInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setCityRedirectAttributes(RedirectAttributes redirectAttributes, BindingResult bindingResult,
                                           CityVm cityVm, String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(CITY_VM, cityVm);
    }

}


//            Path filepath = Paths.get("C:\\Users\\mtae\\Downloads", multipartFile.getOriginalFilename() + suffix + ".png");
//            suffix++;
//
//            try (OutputStream os = Files.newOutputStream(filepath)) {
//                os.write(multipartFile.getBytes());
//            }