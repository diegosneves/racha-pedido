package diegosneves.github.rachapedido.brokers;

import diegosneves.github.rachapedido.model.NotificationEmail;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import diegosneves.github.rachapedido.utils.JsonAdapter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class EmailSendConsumer {


    private final EmailServiceContract emailService;
    private final JsonAdapter jsonAdapter;

    @Autowired
    public EmailSendConsumer(EmailServiceContract emailService, JsonAdapter jsonAdapter) {
        this.emailService = emailService;
        this.jsonAdapter = jsonAdapter;
    }

    @KafkaListener(topics = "${spring.kafka.topics.send_email}", groupId = "${spring.kafka.consumer.group-id}")
    public void sendEmail(ConsumerRecord<String, String> emailMessage) {
        NotificationEmail notification = jsonAdapter.fromJson(emailMessage.value(), NotificationEmail.class);
        this.emailService.sendEmail(notification);
    }

}
