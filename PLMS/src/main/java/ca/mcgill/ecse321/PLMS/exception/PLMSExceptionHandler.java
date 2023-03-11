package ca.mcgill.ecse321.PLMS.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PLMSExceptionHandler {
    @ExceptionHandler(PLMSException.class)
    public ResponseEntity<String> handleEventRegistrationException(PLMSException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        // TODO: return a custom exception object with a list of errors instead of just a string?
        String message = "";
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            message += fe.getDefaultMessage() + "\n";
        }
        return new ResponseEntity<String>(message, e.getStatusCode());
    }

}
