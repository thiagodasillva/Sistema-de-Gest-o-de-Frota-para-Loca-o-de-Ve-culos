package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestão de Frota e Locação",
                version = "1.0",
                description = "Documentação da API para o sistema de gestão de frota e locação de veículos."
        ),
        security = @SecurityRequirement(name = "bearerAuth") // Aplica segurança globalmente
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Token JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {


}
