package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.constant.DayOfWeekEnum;
import com.kokotripadmin.constant.TradingHourTypeEnum;
import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.dto.tourspot.TourSpotInfoDto;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.day_of_week.DayOfWeekNotFoundException;
import com.kokotripadmin.exception.region.RegionMismatchException;
import com.kokotripadmin.exception.region.RegionNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.trading_hour_type.TradingHourTypeNotFoundException;
import com.kokotripadmin.service.interfaces.*;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.region.RegionVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotInfoVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
import java.util.List;

@Controller
@RequestMapping("/tour-spot")
public class TourSpotController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TourSpotService tourSpotService;

    @Autowired
    private CityService cityService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private Convert convert;

    private final String TOUR_SPOT_VM = "tourSpotVm";

    @GetMapping("/list")
    public String listTourSpot() {
        return "tour-spot/tour-spot-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<TourSpotDto> listTourSpotPaginated(@Valid DataTablesInput dataTablesInput) {
        return tourSpotService.findAllByPagination(dataTablesInput);
    }

    @GetMapping("/add")
    public String addTourSpot(Model model) {

        if (!model.containsAttribute(TOUR_SPOT_VM))
            model.addAttribute(TOUR_SPOT_VM, new TourSpotVm(true));
        setAddTourSpotAttributes(model);
        return "tour-spot/tour-spot-form";
    }

    @GetMapping("/edit/{id}")
    public String editTourSpot(Model model, @PathVariable("id") Integer id) throws TourSpotNotFoundException {
        model.addAttribute(TOUR_SPOT_VM, modelMapper.map(tourSpotService.findById(id), TourSpotVm.class));
        setAddTourSpotAttributes(model);
        return "tour-spot/tour-spot-form";
    }

    @PostMapping("/save")
    public String saveTourSpot(@Valid @ModelAttribute("tourSpotVm") TourSpotVm tourSpotVm,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setTourSpotRedirectAttributes(redirectAttributes, bindingResult, tourSpotVm, "");
            return "redirect:/tour-spot/add";
        }

        try {
            TourSpotDto tourSpotDto = modelMapper.map(tourSpotVm, TourSpotDto.class);
            Integer tourSpotId = tourSpotService.save(tourSpotDto);
            return "redirect:/tour-spot/detail/" + tourSpotId;
        } catch (SupportLanguageNotFoundException | CityNotFoundException | RegionNotFoundException |
                TourSpotNameDuplicateException | TourSpotNotFoundException | TagNotFoundException |
                TourSpotInfoAlreadyExistsException | TagInfoNotFoundException | RegionMismatchException | DayOfWeekNotFoundException |
                TradingHourTypeNotFoundException | TourSpotInfoNotFoundException exception) {
            setTourSpotRedirectAttributes(redirectAttributes, bindingResult, tourSpotVm, exception.getMessage());
            return "redirect:/tour-spot/add";
        }
    }

    @GetMapping("/detail/{id}")
    public String detailTourSpot(@PathVariable("id") Integer tourSpotId, Model model) throws TourSpotNotFoundException {

        setTourSpotAttributes(model);
        TourSpotDto tourSpotDto = tourSpotService.findByIdInDetail(tourSpotId);
        TourSpotVm tourSpotViewModel = modelMapper.map(tourSpotDto, TourSpotVm.class);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        model.addAttribute(TOUR_SPOT_VM, tourSpotViewModel);
        return "tour-spot/tour-spot-detail-page";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTourSpot(@RequestParam("id") Integer tourSpotId) {
        try {
            tourSpotService.delete(tourSpotId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tour-spot/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지는 삭제할수없습니다."));
        } catch (TourSpotNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @GetMapping(value = "/auto-complete-list", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> listAutoCompleteTourSpot(@RequestParam("search") String search)
    throws JsonProcessingException {
        List<LocatableAutoCompleteDto> locatableAutoCompleteDtoList = tourSpotService.findAllAsLocatableAutoComplete(search);
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(locatableAutoCompleteDtoList));
    }

    private void setAddTourSpotAttributes(Model model) {
        List<RegionDto> regionDtoList = regionService.findAllByEnabledAndCityEnabled(true, true);
        List<RegionVm> regionViewModelList = modelMapper.map(regionDtoList, new TypeToken<List<RegionVm>>() {}.getType());

        model.addAttribute(AppConstant.REGION_VM_LIST, regionViewModelList);
        model.addAttribute(AppConstant.CITY_LINKED_HASH_MAP, cityService.findAllAsLinkedHashMap());
        setTourSpotAttributes(model);
    }


    private void setTourSpotAttributes(Model model) {
        model.addAttribute(AppConstant.TAG_LINKED_HASH_MAP, tagService.findAllAsLinkedHashMap());
        model.addAttribute(AppConstant.TRADING_HOUR_TYPE_LINKED_HASH_MAP, TradingHourTypeEnum.convertToLinkedHashMap());
        model.addAttribute(AppConstant.DAY_OF_WEEK_LINKED_HASH_MAP, DayOfWeekEnum.convertToLinkedHashMap());
    }


//    =====================================TOUR_SPOT_INFO===================================================== //


    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveTourSpotInfo(@ModelAttribute @Valid TourSpotInfoVm tourSpotInfoVm,
                                                   BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            TourSpotInfoDto tourSpotInfoDto = modelMapper.map(tourSpotInfoVm, TourSpotInfoDto.class);
            tourSpotInfoDto = tourSpotService.saveInfo(tourSpotInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(tourSpotInfoDto));
        } catch (SupportLanguageNotFoundException | TourSpotNotFoundException | TourSpotInfoNotFoundException |
                TagInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotInfoAlreadyExistsException | TourSpotInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteTourSpotInfo(@RequestParam("id") Integer tourSpotInfoId) {

        try {
            tourSpotService.deleteInfo(tourSpotInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(tourSpotInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지 번역정보는 삭제할수없습니다."));
        } catch (TourSpotInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }



    private void setTourSpotRedirectAttributes(RedirectAttributes redirectAttributes,
                                               BindingResult bindingResult,
                                               TourSpotVm tourSpotViewModel,
                                               String exceptionMessage) {
        setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(TOUR_SPOT_VM, tourSpotViewModel);
    }


}
