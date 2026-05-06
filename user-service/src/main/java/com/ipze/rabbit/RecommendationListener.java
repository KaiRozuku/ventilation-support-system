package com.ipze.rabbit;

import com.ipze.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendationListener {

    private final RecommendationWebSocketService recommendationWebSocketService;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void consume(RecommendationMessage message) {
        recommendationWebSocketService.send(message);
    }
}