package edu.trincoll.restrecipeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("REST Recipe Service")
                        .description("Currently only works as an ingredient database. All ingredient values are required except for the unit, which can hava a null value.")
                        .version("0.0.1-SNAPSHOT"));
    }
}
