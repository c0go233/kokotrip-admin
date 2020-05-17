package com.kokotripadmin.controller;


import com.kokotripadmin.exception.city.CityNameDuplicateException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketPriceNotFoundException;
import com.kokotripadmin.viewmodel.exception.ExceptionViewModel;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@ControllerAdvice
public class GlobalExceptionHandler {


//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(Exception exception, HttpServletRequest request) throws Exception {
//        ModelAndView modelAndView = new ModelAndView();
//
//
//        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
//            System.out.println(stackTraceElement.toString());
//        }
//
//
//        String toString = exception.toString();
//        String simple = exception.getClass().getSimpleName();
//        String name = exception.getClass().getName();
//
//        modelAndView.setViewName("/error/error-page");
//        return modelAndView;
//    }

//    @ExceptionHandler({CityInfoNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody // you can get exception in ajax through xhr.responseJSON
//    public Exception handleNotFoundExceptionForJson(Exception exception, HttpServletResponse response) throws Exception {
//
//        return exception;
//    }

//    @ExceptionHandler(DataAccessException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView handleDataAccessException(Exception exception) {
//        return getModelAndViewWithException(exception);
//    }

    @ExceptionHandler({CityNameDuplicateException.class, BadHttpRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadRequestException(Exception exception) {
        return getModelAndViewWithException(exception);
    }

    @ExceptionHandler({CityNotFoundException.class, StateNotFoundException.class, SupportLanguageNotFoundException.class,
                       TourSpotTicketPriceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception exception) {
        return getModelAndViewWithException(exception);
    }

    private ModelAndView getModelAndViewWithException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        ExceptionViewModel exceptionViewModel = new ExceptionViewModel(exception.getClass().getName(), exception.getMessage(), exception.getStackTrace());
        modelAndView.addObject("exceptionViewModel", exceptionViewModel);
        modelAndView.setViewName("/error/error-page");
        return modelAndView;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
