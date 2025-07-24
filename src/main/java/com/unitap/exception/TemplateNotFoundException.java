package com.unitap.exception;

public class TemplateNotFoundException extends NotFoundException {

  public TemplateNotFoundException() {
    super("exception.template.not-found");
  }
}
