package kaixin.learning.kafkatransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KafkaTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaTransactionApplication.class, args);
    }

}
