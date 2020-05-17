package com.kokotripadmin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionInfoDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.tour_spot.ticket.*;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotTicketDescriptionService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotTicketService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.tourspot.TourSpotTicketDescriptionInfoVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotTicketDescriptionVm;
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
@RequestMapping("tour-spot/ticket/description")
public class TourSpotTicketDescriptionController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TourSpotTicketDescriptionService tourSpotTicketDescriptionService;

    @Autowired
    private TourSpotTicketService tourSpotTicketService;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private Convert convert;

    private final String TOUR_SPOT_TICKET_DESCRIPTION_VM = "tourSpotTicketDescriptionVm";


    @GetMapping("/add")
    public String addTourSpotTicketDescription(Model model, @RequestParam("tourSpotTicketId") Integer tourSpotTicketId)
    throws TourSpotTicketNotFoundException {

        if (!model.containsAttribute(TOUR_SPOT_TICKET_DESCRIPTION_VM)) {
            TourSpotTicketDto tourSpotTicketDto = tourSpotTicketService.findById(tourSpotTicketId);
            TourSpotTicketDescriptionVm tourSpotTicketDescriptionVm = new TourSpotTicketDescriptionVm(tourSpotTicketDto.getTourSpotId(),
                                                                                                      tourSpotTicketDto.getTourSpotName(),
                                                                                                      tourSpotTicketDto.getId(),
                                                                                                      tourSpotTicketDto.getName(),
                                                                                                      true);
            model.addAttribute(TOUR_SPOT_TICKET_DESCRIPTION_VM, tourSpotTicketDescriptionVm);
        }
        return "tour-spot/ticket/description/tour-spot-ticket-description-form";
    }


    @GetMapping("/edit/{id}")
    public String editTourSpotTicketDescription(Model model, @PathVariable("id") Integer tourSpotTicketDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException {

        TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto = tourSpotTicketDescriptionService.findById(tourSpotTicketDescriptionId);
        TourSpotTicketDescriptionVm tourSpotTicketDescriptionVm = modelMapper.map(tourSpotTicketDescriptionDto, TourSpotTicketDescriptionVm.class);
        model.addAttribute(TOUR_SPOT_TICKET_DESCRIPTION_VM, tourSpotTicketDescriptionVm);
        return "tour-spot/ticket/description/tour-spot-ticket-description-form";
    }


    @GetMapping("/detail/{id}")
    public String detailTourSpotTicketDescription(Model model, @PathVariable("id") Integer tourSpotDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException {

        TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto =
                tourSpotTicketDescriptionService.findByIdInDetail(tourSpotDescriptionId);
        TourSpotTicketDescriptionVm tourSpotTicketDescriptionVm = modelMapper.map(tourSpotTicketDescriptionDto,
                                                                            TourSpotTicketDescriptionVm.class);

        model.addAttribute(TOUR_SPOT_TICKET_DESCRIPTION_VM, tourSpotTicketDescriptionVm);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "tour-spot/ticket/description/tour-spot-ticket-description-detail-page";
    }

    @PostMapping("/save")
    public String saveTourSpotTicketDescription(@Valid @ModelAttribute("tourSpotTicketDescriptionVm") TourSpotTicketDescriptionVm tourSpotTicketDescriptionVm,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) throws TourSpotNotFoundException {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, tourSpotTicketDescriptionVm, "");
            return "redirect:/tour-spot/ticket/description/add";
        }

        try {
            TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto = modelMapper.map(tourSpotTicketDescriptionVm, TourSpotTicketDescriptionDto.class);
            Integer tourSpotDescriptionId = tourSpotTicketDescriptionService.save(tourSpotTicketDescriptionDto);
            return "redirect:/tour-spot/ticket/description/detail/" + tourSpotDescriptionId;
        } catch (TourSpotTicketDescriptionAlreadyExistsException | TourSpotTicketDescriptionInfoAlreadyExistsException |
                TourSpotTicketInfoNotFoundException | TourSpotTicketDescriptionNotFoundException | SupportLanguageNotFoundException |
                TourSpotTicketDescriptionInfoNotFoundException | TourSpotTicketNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, tourSpotTicketDescriptionVm, exception.getMessage());
            return "redirect:/tour-spot/ticket/description/add";
        }
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTourSpotTicketDescription(@RequestParam("id") Integer tourSpotTicketDescriptionId) {
        try {
            Integer tourSpotTicketId = tourSpotTicketDescriptionService.delete(tourSpotTicketDescriptionId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tour-spot/ticket/detail/" + tourSpotTicketId;
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지티켓설명은 삭제할수없습니다."));
        } catch (TourSpotTicketDescriptionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveTourSpotTicketDescriptionInfo(@Valid @ModelAttribute
                                                                    TourSpotTicketDescriptionInfoVm tourSpotTicketDescriptionInfoVm,
                                                                    BindingResult bindingResult)
    throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));
        try {
            TourSpotTicketDescriptionInfoDto tourSpotTicketDescriptionInfoDto =
                    modelMapper.map(tourSpotTicketDescriptionInfoVm, TourSpotTicketDescriptionInfoDto.class);
            tourSpotTicketDescriptionInfoDto = tourSpotTicketDescriptionService.saveInfo(tourSpotTicketDescriptionInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(tourSpotTicketDescriptionInfoDto));
        } catch (TourSpotTicketDescriptionInfoNotEditableException | TourSpotTicketDescriptionInfoAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotTicketDescriptionNotFoundException | TourSpotTicketInfoNotFoundException | TourSpotTicketDescriptionInfoNotFoundException |
                SupportLanguageNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteTourSpotTicketDescriptionInfo(@RequestParam("id") Integer tourSpotTicketDescriptionInfoId) {

        try {
            tourSpotTicketDescriptionService.deleteInfo(tourSpotTicketDescriptionInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(tourSpotTicketDescriptionInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지티켓설명 번역정보는 삭제할수없습니다."));
        } catch (TourSpotTicketDescriptionInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotTicketDescriptionInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       TourSpotTicketDescriptionVm tourSpotTicketDescriptionVm,
                                       String exceptionMessage) {

        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(TOUR_SPOT_TICKET_DESCRIPTION_VM, tourSpotTicketDescriptionVm);
        redirectAttributes.addAttribute("tourSpotTicketId", tourSpotTicketDescriptionVm.getTourSpotTicketId());
    }



}
