package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.exception.city.*;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    private final String CITY_IMAGE_DIRECTORY = BUCKET_NAME + "/city/image";


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
    public String addCity(Model model, @RequestParam(value = "stateId", required = false) Integer stateId) throws StateNotFoundException {

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

    @PostMapping(value = "/image/save", consumes = {"multipart/form-data"}, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> uploadImage(@Valid @RequestParam("image") MultipartFile multipartFile,
                                              @Valid @RequestParam("fileName") String fileName,
                                              @Valid @RequestParam("cityId") Integer cityId) {



        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson("시티사진에러에러"));


//        if (cityService.imageExistsByIdAndImageName(cityId, fileName)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .body(convert.exceptionToJson("duplicate:" + fileName));
//        } else {
//            try {
//                Integer cityImageId = cityService.saveImage(cityId, CITY_IMAGE_DIRECTORY, fileName, multipartFile.getContentType());
//                return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(cityImageId.toString()));
//            } catch (CityNotFoundException exception) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
//            }
//
//            //TODO: upload to S3
//
//        }
    }


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