package kaixin.learning.kafkastream.config;

import kaixin.learning.kafkastream.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Configuration
@Slf4j
public class KafkaStreamsConfig {

    private static final double DAILY_LIMIT = 50_000;

    @Bean
    public KStream<String, Transaction> fraudDetectionStream(StreamsBuilder builder) {
        JsonSerde<Transaction> transactionSerde = new JsonSerde<>(Transaction.class);

        // Typed stream from "transactions" topic
        KStream<String, Transaction> transactionsStream = builder.stream(
                "transactions",
                Consumed.with(Serdes.String(), transactionSerde)
        );

        KTable<Windowed<String>, Double> dailyTotals = transactionsStream
                .groupBy((key, transaction) -> {
                    Instant instant = Instant.ofEpochMilli(transaction.getTimestamp());
                    LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    return transaction.getAccountId() + "|" + date.toString();
                })
                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofDays(1), Duration.ofMinutes(1)))
                .aggregate(
                        () -> 0.0,
                        (key, transaction, aggregate) -> aggregate + transaction.getAmount(), // adder
                        Materialized.with(Serdes.String(), Serdes.Double())
                );

        dailyTotals.toStream()
                   .map((windowedKey, total) -> {
                       String[] parts = windowedKey.key().split("\\|");
                       String accountId = parts[0];
                       return new KeyValue<>(accountId, total);
                   })
                   .filter((key, value) -> value > DAILY_LIMIT)
                   .map((key, val) -> new KeyValue<>(key, String.format("Fraud detected for amount: %f", val)))
                   .to("daily-transaction-totals", Produced.with(Serdes.String(), Serdes.String()));

        return transactionsStream;
    }
}
