package com.aiimage.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "AI Image Evidence API",
        version = "v.6.6.6.1",
        description = "API para gerenciamento de evidências fotográficas de ordens de serviço.\n\n" +
                     "Funcionalidades principais:\n" +
                     "* Upload de imagens (chegada e conclusão)\n" +
                     "* Validação automática com LLMs\n" +
                     "* Extração de metadados EXIF\n" +
                     "* Armazenamento seguro com retenção configurável",
        contact = @Contact(
            name = "Time de Desenvolvimento",
            email = "time-tech@yourcompany.local",
            url = "https://github.com/morthagor/image-ai"
        ),
        license = @License(
            name = "Proprietary",
            url = "https://yourcompany.local/licenses"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Servidor Local"
        ),
        @Server(
            url = "https://api.yourcompany.local",
            description = "Servidor de Produção"
        )
    }
)
public class OpenApiConfig {
}
