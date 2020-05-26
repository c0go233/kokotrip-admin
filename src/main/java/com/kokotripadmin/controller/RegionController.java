package com.kokotripadmin.controller;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.region.RegionInfoDto;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.exception.city.CityInfoNotDeletableException;
import com.kokotripadmin.exception.city.CityInfoNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.region.*;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.interfaces.CityService;
import com.kokotripadmin.service.interfaces.RegionService;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.city.CityVm;
import com.kokotripadmin.viewmodel.region.RegionInfoVm;
import com.kokotripadmin.viewmodel.region.RegionVm;
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
@RequestMapping("/region")
public class RegionController extends BaseController {


    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Convert convert;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupportLanguageService supportLanguageService;

    private final String REGION_VM = "regionVm";


    @GetMapping("/list")
    public String showRegionList() {
        return "region/region-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<RegionDto> showRegionListPaginated(@Valid DataTablesInput input) {
        return regionService.findAllByPagination(input);
    }

    @GetMapping("/add")
    public String addRegion(Model model, @RequestParam(value = "cityId", required = false) Integer cityId)
    throws CityNotFoundException {

        RegionVm regionVm = model.containsAttribute(REGION_VM) ? (RegionVm) model.asMap().get(REGION_VM) : new RegionVm(true);

        if (cityId != null) {
            CityDto cityDto = cityService.findById(cityId);
            regionVm.setMetaData(cityDto.getStateName(), cityDto.getStateId(), cityDto.getName(), cityDto.getId());
        }

        model.addAttribute(REGION_VM, regionVm);
        model.addAttribute(AppConstant.CITY_LINKED_HASH_MAP, cityService.findAllAsLinkedHashMap());
        return "region/region-form";
    }

    @GetMapping("/detail/{id}")
    public String detailRegion(Model model, @PathVariable("id") int regionId) throws RegionNotFoundException {

        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        model.addAttribute(REGION_VM, modelMapper.map(regionService.findByIdInDetail(regionId), RegionVm.class));
        return "region/region-detail-page";
    }


    @GetMapping("/edit/{id}")
    public String editRegion(Model model, @PathVariable("id") int regionId) throws RegionNotFoundException {
        model.addAttribute(REGION_VM, modelMapper.map(regionService.findById(regionId), RegionVm.class));
        model.addAttribute(AppConstant.CITY_LINKED_HASH_MAP, cityService.findAllAsLinkedHashMap());
        return "region/region-form";
    }

    @PostMapping("/save")
    public String saveRegion(@Valid @ModelAttribute("regionVm") RegionVm regionVm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, regionVm, "");
            redirectAttributes.addAttribute("cityId", regionVm.getCityId());
            return "redirect:/region/add";
        }

        try {
            RegionDto regionDto = modelMapper.map(regionVm, RegionDto.class);
            return "redirect:/region/detail/" + regionService.save(regionDto);
        } catch (CityInfoNotFoundException | SupportLanguageNotFoundException | RegionNameDuplicateException | CityNotFoundException |
                RegionNotFoundException | RegionInfoAlreadyExistsException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, regionVm, exception.getMessage());
            redirectAttributes.addAttribute("cityId", regionVm.getCityId());
            return "redirect:/region/add";
        }
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteRegion(@RequestParam("id") Integer regionId) {
        try {
            regionService.delete(regionId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/region/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 유명지역은 삭제할수없습니다."));
        } catch (RegionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

//  =============================== IMAGE ============================================  //

    @PostMapping(value = "/image/save",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveRegionImage(@RequestParam("image") MultipartFile multipartFile,
                                                @RequestParam("fileName") String fileName,
                                                @RequestParam("regionId") Integer regionId,
                                                @RequestParam("order") Integer order,
                                                @RequestParam("repImage") boolean repImage) {
        try {
            RegionImageDto cityImageDto = new RegionImageDto(fileName, multipartFile.getContentType(), order,
                                                         repImage, regionId, multipartFile);
            Integer cityImageId = cityService.saveImage(cityImageDto);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(cityImageId.toString()));
        } catch (AmazonServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (RegionNotFoundException | FileIsNotImageException | ImageDuplicateException | IOException |
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
        } catch (RegionImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(e.getMessage()));
        }
    }

    @PostMapping(value = "/image/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteRegionImage(@RequestParam("id") Integer imageId) {

        try {
            cityService.deleteImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(imageId.toString()));
        } catch (AmazonServiceException | RegionImageNotFoundException | RepImageNotDeletableException e) {
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


//  =============================== REGION INFO ===================================== //


    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteRegionInfo(@RequestParam("id") Integer regionInfoId) {

        try {
            regionService.deleteInfo(regionInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(regionInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 유명지역번역정보는 삭제할수없습니다."));
        } catch (RegionInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (RegionInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveRegionInfo(@ModelAttribute @Valid RegionInfoVm regionInfoVm,
                                                 BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            RegionInfoDto regionInfoDto = modelMapper.map(regionInfoVm, RegionInfoDto.class);
            regionInfoDto = regionService.saveInfo(regionInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(regionInfoDto));
        } catch (CityInfoNotFoundException | SupportLanguageNotFoundException | RegionInfoNotFoundException | RegionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (RegionInfoAlreadyExistsException | RegionNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       RegionVm regionVm,
                                       String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(REGION_VM, regionVm);
    }

}
