package de.telran.pizzaproject.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
                log.error("Exception during execution of SpringSecurity application1");
                return "error";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
                log.error("Exception during execution of SpringSecurity application2");
                return "error";
            }
        }
        log.error("Http response" + status.toString());
        model.addAttribute("httpStatus", HttpStatus.BAD_REQUEST);
        return "error";
    }

//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String exception(final Throwable throwable, final Model model) {
//        log.error("Exception during execution of SpringSecurity application3", throwable);
//        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
//        model.addAttribute("error", errorMessage);
//        return "error";
//    }

}
