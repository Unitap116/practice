package com.unitap.controller.handler;

import com.unitap.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public void handleServiceException() {
        /* TODO implement handling implementation */
    }
}
