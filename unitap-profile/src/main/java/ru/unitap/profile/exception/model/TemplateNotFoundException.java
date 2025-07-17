package ru.unitap.profile.exception.model;

public class TemplateNotFoundException extends NotFoundException {

  public TemplateNotFoundException() {
    super("exception.template.not-found");
  }
}
