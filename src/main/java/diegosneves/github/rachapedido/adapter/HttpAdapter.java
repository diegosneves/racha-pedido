package diegosneves.github.rachapedido.adapter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A classe {@link HttpAdapter} é uma classe base abstrata que fornece uma implementação comum
 * para fazer requisições HTTP usando RestTemplateSimpleWebClient e HttpHeaders.
 *
 * @author diegosneves
 */
@Component
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class HttpAdapter {

    protected RestTemplateSimpleWebClient restTemplateSimpleWebClient;
    protected HttpHeaders headers;

    protected HttpAdapter() {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        this.restTemplateSimpleWebClient = new RestTemplateSimpleWebClient();
    }
}
