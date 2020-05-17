package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotDao;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.activity.ActivityInfoDto;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;
import com.kokotripadmin.service.interfaces.activity.ActivityService;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.TagService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.activity.ActivityInfoVm;
import com.kokotripadmin.viewmodel.activity.ActivityVm;
import com.kokotripadmin.viewmodel.region.RegionVm;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TourSpotService tourSpotService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Convert convert;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ACTIVITY_VM = "activityVm";

    @GetMapping("/list")
    public String listActivity() {
        return "activity/activity-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<ActivityDto> listActivityPaginated(@Valid DataTablesInput dataTablesInput) {
        return activityService.findAllByPagination(dataTablesInput);
    }

    @GetMapping(value = "/auto-complete-list", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> listAutoCompleteActivity(@RequestParam("search") String search)
    throws JsonProcessingException {
        List<LocatableAutoCompleteDto> locatableAutoCompleteDtoList = activityService.findAllAsLocatableAutoComplete(search);
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(locatableAutoCompleteDtoList));
    }

    @GetMapping("/add")
    public String addActivity(@RequestParam(value = "tourSpotId", required = false) Integer tourSpotId, Model model)
    throws TourSpotNotFoundException {

        ActivityVm activityVm = model.containsAttribute(ACTIVITY_VM) ?
                                (ActivityVm) model.asMap().get(ACTIVITY_VM) :
                                new ActivityVm(true);

        if (tourSpotId != null) {
            TourSpotDto tourSpotDto = tourSpotService.findById(tourSpotId);
            activityVm.setTourSpotId(tourSpotId);
            activityVm.setTourSpotName(tourSpotDto.getName());
            activityVm.setLatitude(tourSpotDto.getLatitude());
            activityVm.setLongitude(tourSpotDto.getLongitude());
        }

        model.addAttribute(AppConstant.TAG_LINKED_HASH_MAP, tagService.findAllAsLinkedHashMap());
        model.addAttribute(ACTIVITY_VM, activityVm);
        return "/activity/activity-form";
    }

    @GetMapping("/edit/{id}")
    public String editActivity(@PathVariable("id") Integer activityId, Model model) throws ActivityNotFoundException {
        ActivityDto activityDto = activityService.findById(activityId);
        ActivityVm activityVm = modelMapper.map(activityDto, ActivityVm.class);
        model.addAttribute(ACTIVITY_VM, activityVm);
        model.addAttribute(AppConstant.TAG_LINKED_HASH_MAP, tagService.findAllAsLinkedHashMap());
        return "activity/activity-form";
    }

    @PostMapping("/save")
    public String saveActivity(@Valid @ModelAttribute("activityVm") ActivityVm activityVm,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityVm, "");
            redirectAttributes.addAttribute("tourSpotId", activityVm.getTourSpotId());
            return "redirect:/activity/add";
        }

        try {
            ActivityDto activityDto = modelMapper.map(activityVm, ActivityDto.class);
            return "redirect:/activity/detail/" + activityService.save(activityDto);
        } catch (ActivityNotFoundException | SupportLanguageNotFoundException | TagInfoNotFoundException |
                TourSpotNotFoundException | ActivityInfoAlreadyExistsException | TagNotFoundException |
                TourSpotInfoNotFoundException | ActivityNameDuplicateException | ActivityInfoNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, activityVm, exception.getMessage());
            return "redirect:/activity/add";
        }
    }

    @GetMapping("/detail/{id}")
    public String detailActivity(@PathVariable("id") int activityId, Model model) throws ActivityNotFoundException {
        ActivityDto activityDto = activityService.findByIdInDetail(activityId);
        ActivityVm activityVm = modelMapper.map(activityDto, ActivityVm.class);
        model.addAttribute(ACTIVITY_VM, activityVm);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "activity/activity-detail-page";
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteActivity(@RequestParam("id") Integer activityId) {
        try {
            Integer tourSpotId = activityService.delete(activityId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tour-spot/detail/" + tourSpotId;
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지는 삭제할수없습니다."));
        } catch (ActivityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }


    //  ====================================== INFO ================================================  //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveActivityInfo(@ModelAttribute @Valid ActivityInfoVm activityInfovm,
                                                   BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));
        try {
            ActivityInfoDto activityInfoDto = modelMapper.map(activityInfovm, ActivityInfoDto.class);
            activityInfoDto = activityService.saveInfo(activityInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(activityInfoDto));
        } catch (SupportLanguageNotFoundException | ActivityNotFoundException | ActivityInfoNotFoundException |
                TagInfoNotFoundException | TourSpotInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityInfoAlreadyExistsException | ActivityInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteActivityInfo(@RequestParam("id") Integer activityInfoId) {

        try {
            activityService.deleteInfo(activityInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(activityInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 액티비티 번역정보는 삭제할수없습니다."));
        } catch (ActivityInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (ActivityInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }



    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       ActivityVm activityVm,
                                       String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(ACTIVITY_VM, activityVm);
    }


}
