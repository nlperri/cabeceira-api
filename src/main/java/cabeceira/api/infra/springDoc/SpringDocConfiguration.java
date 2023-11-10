package cabeceira.api.infra.springDoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                        .bearerFormat("JWT")))
                        .info(new Info()
                                .title("Cabeceira API")
                                .description("O aplicativo Cabeceira API é uma aplicação back-end desenvolvida em Spring Boot, projetada para fornecer funcionalidades de gerenciamento de biblioteca pessoal. A aplicação permite aos usuários explorar uma variedade de obras, adicioná-las a estantes específicas e organizar sua biblioteca de maneira eficiente.")
                                .contact(new Contact()
                                    .name("Time Backend")
                                    .email("startDB5@start.db"))
                        );   
                                
    }
}
