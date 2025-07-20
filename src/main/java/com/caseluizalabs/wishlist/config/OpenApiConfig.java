package com.caseluizalabs.wishlist.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wishlist API")
                        .version("v1")
                        .description("API to manage customer wishlists, allowing adding, removing, and retrieving favorite products."));
    }
}