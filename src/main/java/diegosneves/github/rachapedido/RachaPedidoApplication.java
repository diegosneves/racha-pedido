package diegosneves.github.rachapedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class RachaPedidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RachaPedidoApplication.class, args);
    }

}
