package diegosneves.github.rachapedido.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Classe de configuração para a documentação {@link OpenAPI} (Swagger).
 *
 * @author diegosneves
 */
@Configuration
public class OpenApiConfig {

    /**
     * Customiza o objeto OpenAPI.
     * <p>
     * Este método cria uma nova instância de {@link OpenAPI}, inclui informações adicionais através do método {@link OpenApiConfig#getInfo() getInfo()} e
     * define as tags usando o método getTags().
     *
     * @return Uma instância personalizada de {@link OpenAPI} com informações e tags adicionais definidas.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getInfo())
                .tags(getTags());
    }

    /**
     * <p>Retorna um objeto Info contendo informações básicas sobre a API.</p>
     *
     * <p>
     *
     * @return um objeto Info com:
     * <ul>
     *   <li><b>Versão da API:</b> "v0.0.1"</li>
     *   <li><b>Título:</b> "Racha Pedido"</li>
     *   <li><b>Descrição:</b> "API do Racha Pedido"</li>
     *   <li><b>Detalhes de Contato:</b> contendo email, url do repositório e o nome do autor.
     *      <ul>
     *        <li>Email: "neves.diegoalex@outlook.com"</li>
     *        <li>URL: "<a href="https://github.com/diegosneves/racha-pedido">...</a>"</li>
     *        <li>Nome: "Diego Neves"</li>
     *      </ul>
     *   </li>
     * </ul>
     * </p>
     */
    private Info getInfo() {
        return new Info()
                .version("v0.0.1")
                .title("Racha Pedido")
                .description("API do Racha Pedido")
                .contact(new Contact().email("neves.diegoalex@outlook.com").url("https://github.com/diegosneves/racha-pedido").name("Diego Neves"));
    }

    /**
     * Método que retorna uma lista de Tag utilizadas para a documentação do {@link OpenAPI Swagger}.
     *
     * @return List<Tag> - uma lista contendo todas as tags usadas no Swagger.
     * <p>
     * Cada tag é representada por uma instância da classe 'Tag', com um nome e uma descrição.
     * Atualmente, este método retorna uma única tag: "Racha Pedido", que contém operações relacionadas à divisão dos valores do pedido.
     */
    private List<Tag> getTags() {
        return List.of(new Tag().name("Racha Pedido").description("Operações relacionadas a divisão dos valores do pedido"));
    }

}
