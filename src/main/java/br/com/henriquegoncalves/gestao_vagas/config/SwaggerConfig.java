package br.com.henriquegoncalves.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("API de gestão de vagas")
                .description("Api responsável por gestão de vagas - Criação de vagas, candidatos e empresas")
                .version("1"))
                .schemaRequirement("jwt_auth", creatSecurityScheme());
    }

    private SecurityScheme creatSecurityScheme() {
        return new SecurityScheme().name("jwt_auth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    }

}
