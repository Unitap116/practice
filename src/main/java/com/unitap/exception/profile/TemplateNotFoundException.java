package com.unitap.exception.profile;

import com.unitap.exception.NotFoundException;

public class TemplateNotFoundException extends NotFoundException {

  public TemplateNotFoundException() {
    super("exception.template.not-found");
  }
}
