package kaixin.learning.kafkaplay.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoSend {

    @Value("${kafka.topic.name}")
    private String topicName;

    private final MessageProducer messageProducer;

    @Async
    public void sendMessagesAsync() {
        log.info("Beginning to send messages to topic: {}", topicName);
        try {
            for (int i = 0; i < 50; i++) {
                String message = "message-" + i;
                String key = Integer.toString(i);

                CompletableFuture<?> future = messageProducer.sendMessage(topicName, key, message);
                // Wait for each message to be sent before continuing
                future.join();

                // Sleep between messages to avoid flooding
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            log.warn("Message sending was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while sending messages", e);
        }
        log.info("Completed sending all messages");
    }
}
