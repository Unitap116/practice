package com.unitap.exception.profile;

import com.unitap.exception.NotFoundException;

public class ProfileNotFoundException extends NotFoundException {

  public ProfileNotFoundException() {
    super("exception.profile.not-found");
  }
}
