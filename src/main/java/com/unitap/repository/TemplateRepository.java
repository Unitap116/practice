package com.unitap.repository;

import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository {

  Optional<Object> getById(UUID id);
}
