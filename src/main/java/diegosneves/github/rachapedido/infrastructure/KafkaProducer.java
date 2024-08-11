package diegosneves.github.rachapedido.infrastructure;

import diegosneves.github.rachapedido.utils.JsonAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonAdapter jsonAdapter;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, JsonAdapter jsonAdapter) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonAdapter = jsonAdapter;
    }

    public void send(String topic, String message) {
        this.kafkaTemplate.send(topic, message);
    }

    public void convertObjectToJsonAndSend(String topic, Object object) {
        this.kafkaTemplate.send(topic, jsonAdapter.toJson(object));
    }

    public void convertObjectToJsonAndSend(String topic, String key, Object object) {
        this.kafkaTemplate.send(topic, key, jsonAdapter.toJson(object));
    }
}
