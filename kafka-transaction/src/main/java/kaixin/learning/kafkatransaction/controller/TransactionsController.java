package kaixin.learning.kafkatransaction.controller;

import kaixin.learning.kafkatransaction.producer.TransactionsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionsProducer producer;

    @PostMapping
    public void sendTransaction(@RequestParam("error") boolean error) throws InterruptedException {
        producer.sendNotificationBatch(error);
    }
}
