package kaixin.learning.kafkastream.controller;

import kaixin.learning.kafkastream.model.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public TransactionController(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/sendTransaction")
    public String sendTransaction(
            @RequestParam String transactionId,
            @RequestParam String accountId,
            @RequestParam Double amount,
            @RequestParam Long timestamp) {

//        String transaction = String.format("{\"transactionId\":\"%s\",\"accountId\":\"%s\",\"amount\":%f,\"timestamp\":%d}",
//                                           transactionId, accountId, amount, timestamp);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTimestamp(timestamp);

        kafkaTemplate.send("transactions", accountId, transaction);
        return "Transaction sent successfully!";
    }
}
