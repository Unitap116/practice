package ru.unitap.profile.exception.model;

public class ProfileNotFoundException extends NotFoundException {

  public ProfileNotFoundException() {
    super("exception.profile.not-found");
  }
}
