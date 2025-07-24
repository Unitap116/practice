package com.unitap.config;

import com.unitap.config.properties.SwaggerProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition
public class OpenApiConfig {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .servers(List.of(new Server().url(swaggerProperties.getServerUrl())))
                .info(new Info().title("Card Service API").version("1.0.0"));
    }
}
