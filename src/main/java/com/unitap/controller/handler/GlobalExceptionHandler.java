package com.unitap.controller.handler;

import com.unitap.exception.ServiceException;
import com.unitap.utils.BaseExceptionMessage;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResourceBundleMessageSource messageSource;

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

    /* another exceptions will be throwing with 500 status code */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseExceptionMessage> handleCommonException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseExceptionMessage.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }

    /* exception handling from module unitap-profile */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            final MethodArgumentNotValidException ex
    ) {
        final Map<String, String> exceptions = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            val fieldName = ((FieldError) error).getField();
            val errorMessage = messageSource.getMessage(error.getDefaultMessage(), null, LocaleContextHolder.getLocale());
            exceptions.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(exceptions, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseExceptionMessage> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException ex
    ) {

        val errorMessage = messageSource.getMessage(
                "exception.type.mismatch",
                new Object[]{ex.getName(), ex.getRequiredType().getSimpleName()},
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseExceptionMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getClass().getSimpleName())
                        .message(errorMessage)
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseExceptionMessage> handlerBindException(final HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(BaseExceptionMessage.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(exception.getClass().getSimpleName())
                                .message(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        final Map<String, String> exceptions = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            val fieldName = cv.getPropertyPath().toString();
            val errorMessage = messageSource.getMessage(cv.getMessage(), null, LocaleContextHolder.getLocale());
            exceptions.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(exceptions, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<BaseExceptionMessage> handleMultipartException(
            final MultipartException ex
    ) {

        val errorMessage = messageSource.getMessage(
                ex.getMessage(),
                null,
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseExceptionMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getClass().getSimpleName())
                        .message(errorMessage)
                        .build());
    }
}
