package com.unitap.exception;

public class ProfileNotFoundException extends NotFoundException {

  public ProfileNotFoundException() {
    super("exception.profile.not-found");
  }
}
