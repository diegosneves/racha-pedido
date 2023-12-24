package diegosneves.github.rachapedido.adapter;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
public class RestTemplateSimpleWebClient {

    private final RestTemplate restTemplate;

    public RestTemplateSimpleWebClient() {
        this.restTemplate = new RestTemplate();
    }


}
