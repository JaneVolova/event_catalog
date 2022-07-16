package ru.volova.event_catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volova.event_catalog.domain.Event;
import ru.volova.event_catalog.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public Event saveEvent(Event event) {
        return repository.save(event);
    }

    public Event getEvent(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new IllegalStateException("Event not found"));
    }

}
