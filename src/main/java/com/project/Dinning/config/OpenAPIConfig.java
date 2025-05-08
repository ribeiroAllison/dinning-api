package com.project.Dinning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

  @Value("${app.base-url}")
  private String baseUrl;

  @Bean
  public OpenAPI myOpenApi() {
    Server devServer = new Server();
    devServer.setUrl(baseUrl);
    devServer.setDescription("Server URL");

    Contact contact = new Contact();
    contact.setName("Dinning API");
    contact.setEmail("dev.allisonribeiro@gmail.com");
    contact.setUrl("https://github.com/ribeiroAllison/dinning-api");

    License mitLicense = new License()
        .name("MIT License")
        .url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Dining Review API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints for managing restaurants and reviews.")
        .termsOfService("https://www.diningapi.com/terms")
        .license(mitLicense);

    return new OpenAPI()
        .info(info)
        .servers(List.of(devServer));

  }

}
