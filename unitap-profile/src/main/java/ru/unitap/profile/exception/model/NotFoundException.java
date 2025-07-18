package ru.unitap.profile.exception.model;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {

  public NotFoundException(final String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
