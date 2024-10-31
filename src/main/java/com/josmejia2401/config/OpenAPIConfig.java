package com.josmejia2401.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080/meli");
        devServer.setDescription("Servidor para ambiente de desarrollo");

        Contact contact = new Contact();
        contact.setEmail("josmejia.2401@gmail.com");
        contact.setName("Jose Mejia");
        contact.setUrl("https://www.github.com/josmejia2401");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("API para prueba técnica de MELI")
                .version("1.0")
                .contact(contact)
                .description("Se exponen todas los recursos disponibles.").termsOfService("https://www.github.com/josmejia2401/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}