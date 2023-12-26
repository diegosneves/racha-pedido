package diegosneves.github.rachapedido.adapter;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A classe {@link RestTemplateSimpleWebClient} é um encapsulamento simples da classe {@link RestTemplate} do Spring Framework.
 * Ela proporciona uma maneira conveniente de realizar requisições HTTP usando o RestTemplate.
 *
 * Esta classe é anotada com {@link Component @Component}, tornando-a elegível para detecção automática e
 * auto-configuração como um bean do Spring.
 *
 * Esta classe possui um único construtor que inicializa o objeto {@link RestTemplate}.
 *
 * Exemplo de uso:
 *<pre>{@code
 * RestTemplateSimpleWebClient cliente = new RestTemplateSimpleWebClient();
 *
 * cliente.getRestTemplate().getForObject(url, tipoResposta);
 * cliente.getRestTemplate().postForObject(url, requisição, tipoResposta);
 * cliente.getRestTemplate().put(url, requisição);
 * cliente.getRestTemplate().delete(url);
 *
 * }</pre>
 *
 * Nota: substitua url, tipoResposta, requisição com valores adequados de acordo com seu caso de uso.
 *
 * Esta classe fornece um método getter público para acesso ao objeto {@link RestTemplate}:
 *
 * <pre>{@code RestTemplate restTemplate = cliente.getRestTemplate();}</pre>
 *
 * A classe {@link RestTemplateSimpleWebClient} é tipicamente utilizada em conjunto com outras classes,
 * como HttpHeaders, para realizar requisições HTTP com headers apropriados.
 *
 * @autor diegosneves
 */
@Component
@Getter
public class RestTemplateSimpleWebClient {

    private final RestTemplate restTemplate;

    public RestTemplateSimpleWebClient() {
        this.restTemplate = new RestTemplate();
    }


}
