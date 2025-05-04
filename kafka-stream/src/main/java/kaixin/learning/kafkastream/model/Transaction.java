package kaixin.learning.kafkastream.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    private String transactionId;
    private String accountId;
    private Double amount;
    private Long timestamp;
}
