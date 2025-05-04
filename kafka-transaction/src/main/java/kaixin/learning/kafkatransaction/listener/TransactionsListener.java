package kaixin.learning.kafkatransaction.listener;

import kaixin.learning.kafkatransaction.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionsListener {

    @KafkaListener(
            id = "kaixin-notifications",
            topics = "kaixin-transactions",
            groupId = "a",
            concurrency = "3")
    public void listen(Notification notification) {
        log.info("Received: {}", notification.getNotificationId());
    }
}
