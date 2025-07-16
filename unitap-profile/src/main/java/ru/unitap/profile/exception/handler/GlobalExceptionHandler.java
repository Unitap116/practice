package ru.unitap.profile.exception.handler;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import ru.unitap.profile.exception.model.ServiceException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final ResourceBundleMessageSource messageSource;

  @ExceptionHandler(ServiceException.class)
  public final ResponseEntity<ExceptionMessage> handleServiceException(
    final ServiceException exception
  ) {
    val resolvedMessage = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
    return ResponseEntity.status(exception.getStatus())
      .body(ExceptionMessage.builder()
        .exceptionName(exception.getClass().getSimpleName())
        .message(resolvedMessage)
        .build()
      );
  }

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
  public ResponseEntity<ExceptionMessage> handleMethodArgumentTypeMismatch(
    final MethodArgumentTypeMismatchException ex
  ) {

    val errorMessage = messageSource.getMessage(
      "exception.type.mismatch",
      new Object[]{ex.getName(), ex.getRequiredType().getSimpleName()},
      LocaleContextHolder.getLocale()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(ExceptionMessage.builder()
        .exceptionName(ex.getClass().getSimpleName())
        .message(errorMessage)
        .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionMessage handlerBindException(final HttpMessageNotReadableException exception) {
    return ExceptionMessage.builder()
      .exceptionName(exception.getClass().getSimpleName())
      .message(exception.getMessage())
      .build();
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
  public ResponseEntity<ExceptionMessage> handleMultipartException(
    final MultipartException ex
  ) {

    val errorMessage = messageSource.getMessage(
      ex.getMessage(),
      null,
      LocaleContextHolder.getLocale()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(ExceptionMessage.builder()
        .exceptionName(ex.getClass().getSimpleName())
        .message(errorMessage)
        .build());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public final ExceptionMessage onAllExceptions(final Exception exception) {
    return ExceptionMessage.builder()
      .message(exception.getMessage())
      .exceptionName(exception.getClass().getSimpleName())
      .build();
  }
}
