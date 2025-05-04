package kaixin.learning.kafkatransaction.producer;

import kaixin.learning.kafkatransaction.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TransactionsProducer {

    @Value("${kaixin.kafka.transaction-topic}")
    private String topicName;

    long id = 1;
    KafkaTemplate<String, Notification> kafkaTemplate;

    public TransactionsProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void sendNotificationBatch(boolean error) throws InterruptedException {
        generateAndSendPackage(error);
    }

    private void generateAndSendPackage(boolean error)
            throws InterruptedException {
        for (long i = 0; i < 10; i++) {
            Notification n = new Notification(String.valueOf(i), "message-" + id);
            CompletableFuture<SendResult<String, Notification>> result =
                    kafkaTemplate.send(topicName, n.getNotificationId(), n);
            result.whenComplete((sr, ex) ->
                                        log.info("Sent({}): {}", sr.getProducerRecord().key(), sr.getProducerRecord().value()));
            if (error && i > 5)
                throw new RuntimeException();
            Thread.sleep(1000);
        }
    }
}
