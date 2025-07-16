package ru.unitap.profile.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

  private final HttpStatus status;

  public ServiceException(final String message, final HttpStatus status) {
    super(message);
    this.status = status;
  }
}
