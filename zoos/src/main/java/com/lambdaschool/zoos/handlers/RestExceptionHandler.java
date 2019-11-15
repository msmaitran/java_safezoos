package com.lambdaschool.zoos.handlers;

import com.lambdaschool.zoos.exceptions.ResourceFoundException;
import com.lambdaschool.zoos.exceptions.ResourceNotFoundException;
import com.lambdaschool.zoos.models.ErrorDetail;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe,
                                                             HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setTitle("Resource Not Found");
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        errorDetail.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorDetail,
                                    null,
                                    HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceFoundException.class})
    public ResponseEntity<?> handleResourceFoundException(ResourceFoundException rfe,
                                                             HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setTitle("Unexpected Resource");
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDetail(rfe.getMessage());
        errorDetail.setDeveloperMessage(rfe.getClass().getName());
        errorDetail.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorDetail,
                null,
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle(ex.getPropertyName() + " Parameter Type Mismatch");
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(request.getDescription(true));
        errorDetail.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorDetail, headers, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle(ex.getRequestURL());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setDetail(request.getDescription(true));
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");
        errorDetail.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorDetail, headers, HttpStatus.NOT_FOUND);
    }
}
