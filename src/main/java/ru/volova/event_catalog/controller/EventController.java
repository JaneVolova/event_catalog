package ru.volova.event_catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volova.event_catalog.domain.Event;
import ru.volova.event_catalog.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping()
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        return ResponseEntity.ok(this.service.saveEvent(event));
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable Long id) {
        return service.getEvent(id);
    }

}
