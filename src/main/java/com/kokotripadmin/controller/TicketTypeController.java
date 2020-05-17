package com.kokotripadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.ticket.TicketTypeDto;
import com.kokotripadmin.dto.ticket.TicketTypeInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.*;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.service.interfaces.TicketTypeService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.ticket.TicketTypeInfoVm;
import com.kokotripadmin.viewmodel.ticket.TicketTypeVm;
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
@RequestMapping("/ticket-type")
public class TicketTypeController extends BaseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupportLanguageService supportLanguageService;

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private Convert convert;

    private final String TICKET_TYPE_VM = "ticketTypeVm";
    private final String TICKET_TYPE_LIST = "ticketTypeList";


    @GetMapping("/list")
    public String listTicketType(Model model) {
        model.addAttribute(TICKET_TYPE_LIST, ticketTypeService.findAll());
        return "ticket-type/ticket-type-list";
    }

    @GetMapping("/add")
    public String addTicketType(Model model) {

        if (!model.containsAttribute(TICKET_TYPE_VM))
            model.addAttribute(TICKET_TYPE_VM, new TicketTypeVm(true));

        return "ticket-type/ticket-type-form";
    }

    @PostMapping("/save")
    public String saveTheme(@Valid @ModelAttribute("ticketTypeVm") TicketTypeVm ticketTypeVm,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setRedirectAttributes(redirectAttributes, bindingResult, ticketTypeVm, "");
            return "redirect:/ticket-type/add";
        }

        try {
            TicketTypeDto ticketTypeDto = modelMapper.map(ticketTypeVm, TicketTypeDto.class);
            return "redirect:/ticket-type/detail/" + ticketTypeService.save(ticketTypeDto);
        } catch (TicketTypeNameDuplicateException | TicketTypeNotFoundException |
                SupportLanguageNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, bindingResult, ticketTypeVm, exception.getMessage());
            return "redirect:/ticket-type/add";
        } catch (TicketTypeInfoAlreadyExistsException e) {
            return null;
        }
    }

    @GetMapping("/detail/{id}")
    public String detailTicketType(@PathVariable("id") Integer ticketTypeId, Model model) throws TicketTypeNotFoundException {
        TicketTypeDto ticketTypeDto = ticketTypeService.findByIdInDetail(ticketTypeId);
        model.addAttribute(TICKET_TYPE_VM, modelMapper.map(ticketTypeDto, TicketTypeVm.class));
        model.addAttribute(AppConstant.SUPPORT_LANGUAGE_HASH_MAP, supportLanguageService.findAllAsLinkedHashMap());
        return "ticket-type/ticket-type-detail-page";
    }

    @GetMapping("/edit/{id}")
    public String editTicketType(Model model, @PathVariable("id") Integer ticketTypeId) throws TicketTypeNotFoundException {
        TicketTypeDto ticketTypeDto = ticketTypeService.findById(ticketTypeId);
        model.addAttribute(TICKET_TYPE_VM, modelMapper.map(ticketTypeDto, TicketTypeVm.class));
        return "ticket-type/ticket-type-form";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteTicketType(@RequestParam("id") Integer ticketTypeId) {
        try {
            ticketTypeService.delete(ticketTypeId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/ticket-type/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 티켓타입은 삭제할수없습니다."));
        } catch (TicketTypeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }

//  ================================================ TICKET-TYPE INFO ================================================  //

    @PostMapping(value = "/info/save", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> saveThemeInfo(@ModelAttribute @Valid TicketTypeInfoVm ticketTypeInfoVm,
                                                BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.fieldErrorsToJson(bindingResult));

        try {
            TicketTypeInfoDto ticketTypeInfoDto = modelMapper.map(ticketTypeInfoVm, TicketTypeInfoDto.class);
            ticketTypeInfoDto = ticketTypeService.saveInfo(ticketTypeInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(ticketTypeInfoDto));
        } catch (SupportLanguageNotFoundException | TicketTypeInfoNotFoundException | TicketTypeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(e.getMessage()));
        } catch (TicketTypeInfoNotEditableException | TicketTypeInfoAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
    }

    @PostMapping(value = "/info/delete", produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> deleteCityInfo(@RequestParam("id") Integer ticketTypeInfoId) {

        try {
            ticketTypeService.deleteInfo(ticketTypeInfoId);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(ticketTypeInfoId.toString()));
        } catch (TicketTypeInfoNotDeletableException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(exception.getMessage()));
        } catch (TicketTypeInfoNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        }
    }



    private void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                       BindingResult bindingResult,
                                       TicketTypeVm ticketTypeVm,
                                       String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(TICKET_TYPE_VM, ticketTypeVm);
    }

}
