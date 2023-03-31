package com.gbsfo.test.task.listener;

import com.gbsfo.test.task.entity.Event;
import com.gbsfo.test.task.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@RequiredArgsConstructor
public class MessageListener {

    private final EventService eventService;

    @KafkaListener(topics = "CRUD-operation")
    public void listen(String message) {
        eventService.createEvent(new Event(message));
    }
}
