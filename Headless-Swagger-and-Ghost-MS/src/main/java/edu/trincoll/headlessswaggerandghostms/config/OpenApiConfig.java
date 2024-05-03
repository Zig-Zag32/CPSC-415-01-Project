package edu.trincoll.headlessswaggerandghostms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig
{
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Kitchen Inventory and Recipe Book")
                        .description("Interact with the Kitchen Inventory and Recipe Book, and get shopping list and recipe ideas from the LLMs")
                        .version("0.0.1-SNAPSHOT"));
    }
}
