package ru.unitap.profile.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
  info = @Info(
    title = "Profile API",
    description = "API для работы с профилем",
    version = "1.0.0"
  ),
  servers = {
    @Server(
      url = "/",
      description = "Default Server URL"
    )
  }
)
@Configuration
public class SwaggerConfiguration {}
