package com.unitap.controller.handler;

import com.unitap.exception.ServiceException;
import com.unitap.utils.BaseExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<BaseExceptionMessage> handleServiceException(ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus())
                .body(BaseExceptionMessage.builder()
                        .status(serviceException.getHttpStatus().value())
                        .error(serviceException.getClass().getSimpleName())
                        .message(serviceException.getMessage())
                        .build()
                );
    }
}
