package com.kokotripadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.tour_spot.ticket.*;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.TicketTypeService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotTicketService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.tourspot.TourSpotTicketInfoVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotTicketVm;
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
@RequestMapping("/tour-spot/ticket")
public class TourSpotTicketController extends BaseController {


    @Autowired
    private TourSpotTicketService tourSpotTicketService;

    @Autowired
    private TourSpotService tourSpotService;

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

    private final String TOUR_SPOT_TICKET_VM = "tourSpotTicketVm";


    @GetMapping("/add")
    public String addTourSpotTicket(Model model, @RequestParam("tourSpotId") Integer tourSpotId)
    throws TourSpotNotFoundException {

        if (!model.containsAttribute(TOUR_SPOT_TICKET_VM)) {
            String tourSpotName = tourSpotService.getNameById(tourSpotId);
            model.addAttribute(TOUR_SPOT_TICKET_VM, new TourSpotTicketVm(true, tourSpotName, tourSpotId));
        }
        model.addAttribute(AppConstant.TICKET_TYPE_LINKED_HASH_MAP, ticketTypeService.findAllAsLinkedHashMap());
        return "tour-spot/ticket/tour-spot-ticket-form";
    }

    @GetMapping("/edit/{id}")
    public String editTourSpotTicket(Model model, @PathVariable("id") Integer tourSpotId)
    throws TourSpotTicketNotFoundException {
        TourSpotTicketDto tourSpotTicketDto = tourSpotTicketService.findById(tourSpotId);
        TourSpotTicketVm tourSpotTicketVm = modelMapper.map(tourSpotTicketDto, TourSpotTicketVm.class);
        model.addAttribute(TOUR_SPOT_TICKET_VM, tourSpotTicketVm);
        model.addAttribute(AppConstant.TICKET_TYPE_LINKED_HASH_MAP, ticketTypeService.findAllAsLinkedHashMap());
        return "tour-spot/ticket/tour-spot-ticket-form";
    }


    @GetMapping("/detail/{id}")
    public String detailTourSpotTicket(@PathVariable("id") Integer tourSpotTicketId,
                                       Model model) throws TourSpotTicketNotFoundException {
        TourSpotTicketDto tourSpotTicketDto = tourSpotTicketService.findByIdInDetail(tourSpotTicketId);
        TourSpotTicketVm tourSpotTicketVm = modelMapper.map(tourSpotTicketDto, TourSpotTicketVm.class);
        model.addAttribute(TOUR_SPOT_TICKET_VM, tourSpotTicketVm);
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());

        return "tour-spot/ticket/tour-spot-ticket-detail-page";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTourSpotTicket(@RequestParam("id") Integer tourSpotTicketId) {
        try {
            String returnUrl = "redirect:" + super.getBaseUrl() + "/tour-spot/detail/" + tourSpotTicketService.delete(tourSpotTicketId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지티켓은 삭제할수없습니다."));
        } catch (TourSpotTicketNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping("/save")
    public String saveTourSpotTicket(@Valid @ModelAttribute("tourSpotTicketVm") TourSpotTicketVm tourSpotTicketVm,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, tourSpotTicketVm, "");
            redirectAttributes.addAttribute("tourSpotId", tourSpotTicketVm.getTourSpotId());
            return "redirect:/tour-spot/ticket/add";
        }

        try {
            TourSpotTicketDto tourSpotTicketDto = modelMapper.map(tourSpotTicketVm, TourSpotTicketDto.class);
            Integer tourSpotTicketId = tourSpotTicketService.save(tourSpotTicketDto);
            return "redirect:/tour-spot/ticket/detail/" + tourSpotTicketId;
        } catch (TourSpotTicketInfoAlreadyExistsException | SupportLanguageNotFoundException | TourSpotTicketInfoNotFoundException |
                TourSpotNotFoundException | TicketTypeNotFoundException | TourSpotInfoNotFoundException | TourSpotTicketNotFoundException |
                TourSpotTicketNameDuplicateException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, tourSpotTicketVm, exception.getMessage());
            redirectAttributes.addAttribute("tourSpotId", tourSpotTicketVm.getTourSpotId());
            return "redirect:/tour-spot/ticket/add";
        }
    }




//  ================================================= INFO ======================================================== //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveTourSpotTicketInfo(@ModelAttribute @Valid TourSpotTicketInfoVm tourSpotTicketInfoVm,
                                                   BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            TourSpotTicketInfoDto tourSpotTicketInfoDto = modelMapper.map(tourSpotTicketInfoVm, TourSpotTicketInfoDto.class);
            tourSpotTicketInfoDto = tourSpotTicketService.saveInfo(tourSpotTicketInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(tourSpotTicketInfoDto));
        } catch (SupportLanguageNotFoundException | TourSpotTicketNotFoundException | TourSpotTicketInfoNotFoundException |
                TourSpotInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotTicketInfoAlreadyExistsException | TourSpotTicketInfoNotEditableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteTourSpotTicketInfo(@RequestParam("id") Integer tourSpotTicketInfoId) {

        try {
            tourSpotTicketService.deleteInfo(tourSpotTicketInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(tourSpotTicketInfoId.toString()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 여행지티켓 번역정보는 삭제할수없습니다."));
        } catch (TourSpotTicketInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TourSpotTicketInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       TourSpotTicketVm tourSpotTicketVm,
                                       String exceptionMessage) {
        setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(TOUR_SPOT_TICKET_VM, tourSpotTicketVm);
    }


}
