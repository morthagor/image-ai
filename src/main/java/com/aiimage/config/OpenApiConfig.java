package com.aiimage.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "AI Image API", version = "v.6.6.6.0", description = "API para gerenciamento de evidências fotográficas"))
public class OpenApiConfig {
}
