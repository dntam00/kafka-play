package kaixin.learning.kafkaplay.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kaixin-kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) throws InterruptedException {
        log.info("Received message: topic = {}, partition = {}, offset = {}, key = {}, value = {}, thread = {}",
                 record.topic(),
                 record.partition(),
                 record.offset(),
                 record.key(),
                 record.value(),
                 Thread.currentThread().getId());
        Thread.sleep(500);
    }
}
