package ru.volova.event_catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volova.event_catalog.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
