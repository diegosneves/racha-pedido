package diegosneves.github.rachapedido.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getInfo())
                .tags(getTags());
    }

    private Info getInfo() {
        return new Info()
                .version("v0.0.1")
                .title("Racha Pedido")
                .description("API do Racha Pedido")
                .contact(new Contact().email("neves.diegoalex@outlook.com").url("https://github.com/diegosneves/racha-pedido").name("Diego Neves"));
    }

    private List<Tag> getTags() { // TODO - Ajustar as tags do swagger
        return List.of(new Tag().name("Racha Pedido").description("Operações relacionadas a divisão dos valores do pedido"));
    }

}
