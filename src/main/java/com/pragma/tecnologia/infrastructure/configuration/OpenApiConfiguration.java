package com.pragma.tecnologia.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestión de Bootcamps")
                        .version("1.0.0")
                        .description("Documentación para el microservicio de Tecnologías y Bootcamps (WebFlux)"));
    }
}