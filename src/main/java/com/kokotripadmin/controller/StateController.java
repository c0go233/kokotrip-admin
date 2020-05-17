package com.kokotripadmin.controller;

import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.exception.state.StateNameDuplicateException;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.service.interfaces.StateService;
import com.kokotripadmin.util.Convert;
import com.kokotripadmin.viewmodel.state.StateVm;
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
import java.sql.SQLException;

@Controller
@RequestMapping("/state")
public class StateController extends BaseController {

    @Autowired
    private StateService stateService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Convert convert;

    private final String STATE_VM = "stateVm";

    @GetMapping("/list")
    public String showStateList() {
        return "state/state-list";
    }

    @GetMapping(value = "/list/paginated", produces = "application/json; charset=utf8")
    @ResponseBody
    public DataTablesOutput<StateDto> showStateListPaginated(@Valid DataTablesInput input) {
        return stateService.findAllByPagination(input);
    }


    @GetMapping("/add")
    public String addState(Model model) {
        if (!model.containsAttribute(STATE_VM))
            model.addAttribute(STATE_VM, new StateVm(true));
        return "state/state-form";
    }

    @GetMapping("/edit/{id}")
    public String editState(Model model, @PathVariable(value = "id") int stateId) throws StateNotFoundException {
        StateDto stateDto = stateService.findById(stateId);
        StateVm stateVm = modelMapper.map(stateDto, StateVm.class);
        model.addAttribute(STATE_VM, stateVm);
        return "state/state-form";
    }

    @PostMapping("/save")
    public String saveStateForm(@Valid @ModelAttribute("stateVm") StateVm stateVm,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            setStateRedirectAttributes(redirectAttributes, bindingResult, stateVm, "");
            return "redirect:/state/add";
        }

        try {
            Integer stateId = stateService.save(modelMapper.map(stateVm, StateDto.class));
            return "redirect:/state/detail/" + stateId;
        } catch (StateNameDuplicateException | StateNotFoundException exception) {
            setStateRedirectAttributes(redirectAttributes, bindingResult, stateVm, exception.getMessage());
            return "redirect:/state/add";
        }
    }

    @GetMapping("/detail/{id}")
    public String detailState(@PathVariable("id") Integer stateId, Model model) throws StateNotFoundException {
        StateDto stateDto = stateService.findByIdInDetail(stateId);
        model.addAttribute(STATE_VM, modelMapper.map(stateDto, StateVm.class));
        return "state/state-detail-page";
    }

    @PostMapping(value = "/delete", produces = "application/json; charset=utf8")
    public ResponseEntity<String> deleteState(@RequestParam("id") Integer stateId) {
        try {
            stateService.delete(stateId);
            String returnUrl = "redirect:" + super.getBaseUrl() + "/state/list";
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(returnUrl));
        } catch (StateNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convert.exceptionToJson(exception.getMessage()));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson("사용중인 시,도는 삭제할수없습니다."));
        }
    }

    private void setStateRedirectAttributes(RedirectAttributes redirectAttributes, BindingResult bindingResult,
                                            StateVm stateVm, String exceptionMessage) {
        super.setRedirectAttributes(redirectAttributes, bindingResult, exceptionMessage);
        redirectAttributes.addFlashAttribute(STATE_VM, stateVm);
    }
}
