//package de.telran.pizzaproject.controller;
//
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//@ControllerAdvice
//@Log4j2
//public class ErrorController {
//
//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String exception(final Throwable throwable, final Model model) {
//        log.error("Exception during execution of SpringSecurity application", throwable);
//        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }
//
//
//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String exceptionBadRequest(UpdatableException ex, final Model model) {
//        logger.error("Indirizzo errato", ex);
//        String errorMessage = (throwable != null ? ex.getMessage() : "Unknown error");
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }
//}