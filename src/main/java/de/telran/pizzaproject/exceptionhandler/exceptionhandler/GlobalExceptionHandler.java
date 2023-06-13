package de.telran.pizzaproject.exceptionhandler.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionServer(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String exceptionBadRequest(final Exception exception, final Model model) {
//        log.error("Exception ", exception);
//        String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }

//    private final UserValidator validator;
////    private Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
//
//    public GlobalExceptionHandler(UserValidator validator) {
//        this.validator = validator;
//    }
//
//    @InitBinder
//    public void addBinder(WebDataBinder binder) {
//        if (binder.getTarget() != null && binder.getTarget().getClass().equals(User.class)) {
//            binder.addValidators(validator);
//        }
//    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getCode();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<String> handleConstraintValidationExceptions(ConstraintViolationException ex) {
//        return new ResponseEntity<>("not valid due to validation error: " +
//                ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    ResponseEntity<String> handleException(Exception e) {
////        log.error("Error occurred", e);
////        log.info("Info message");
////        log.debug("Debug message");
//        return new ResponseEntity<>("Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
