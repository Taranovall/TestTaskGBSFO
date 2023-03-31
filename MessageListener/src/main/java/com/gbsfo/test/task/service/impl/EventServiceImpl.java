package com.gbsfo.test.task.service.impl;

import com.gbsfo.test.task.entity.Event;
import com.gbsfo.test.task.repository.EventRepository;
import com.gbsfo.test.task.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void createEvent(Event event) {
        eventRepository.save(event);
    }
}
