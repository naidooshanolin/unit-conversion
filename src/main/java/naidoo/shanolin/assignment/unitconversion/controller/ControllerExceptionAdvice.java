package naidoo.shanolin.assignment.unitconversion.controller;

import naidoo.shanolin.assignment.unitconversion.dto.ErrorResponse;
import naidoo.shanolin.assignment.unitconversion.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

}
