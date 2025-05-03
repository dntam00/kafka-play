package kaixin.learning.kafkaplay.producer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMessageSimulation {

    private final DoSend doSend;

    @PostConstruct
    public void init() {
        doSend.sendMessagesAsync();
    }
}
