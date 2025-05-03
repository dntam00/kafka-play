package kaixin.learning.kafkaplay.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    /**
     * Sends a message to the configured Kafka topic
     *
     * @param message the message to be sent
     * @return a CompletableFuture containing the result of the send operation
     */
    public CompletableFuture<SendResult<String, String>> sendMessage(String message) {
        log.info("Sending message to topic {}: {}", topicName, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent successfully: [topic: {}, partition: {}, offset: {}]",
                         result.getRecordMetadata().topic(),
                         result.getRecordMetadata().partition(),
                         result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send message: {}", ex.getMessage(), ex);
            }
        });

        return future;
    }

    /**
     * Sends a message to a specific Kafka topic with a key
     *
     * @param topic the topic to send the message to
     * @param key the message key (for partitioning)
     * @param message the message to be sent
     * @return a CompletableFuture containing the result of the send operation
     */
    public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String key, String message) {
        log.info("Sending message to topic {} with key {}: {}", topic, key, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent successfully: [topic: {}, partition: {}, offset: {}]",
                         result.getRecordMetadata().topic(),
                         result.getRecordMetadata().partition(),
                         result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send message: {}", ex.getMessage(), ex);
            }
        });

        return future;
    }
}