package ru.volova.event_catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import ru.volova.event_catalog.domain.Event;
import ru.volova.event_catalog.domain.EventType;
import ru.volova.event_catalog.repository.EventRepository;

import static java.sql.Date.valueOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository repository;

    private List<Event> eventList = new ArrayList();

    @BeforeEach
    public void repositoryMocking() {
        when(repository.save(any(Event.class))).thenAnswer(invocation -> {
            final Event savedEvent = invocation.getArgument(0);
            savedEvent.setId((long) this.eventList.size());
            this.eventList.add(savedEvent);
            return invocation.getArgument(0);
        });

        when(this.repository.findById(anyLong())).thenAnswer(invocation ->
                this.eventList.stream()
                        .filter(event ->
                                Objects.equals(event.getId(), invocation.getArgument(0))).findFirst());

        eventList.clear();
    }

    @Test
    public void whenAddEventSuccess() throws Exception {

        Event event = new Event(1L, new EventType(3L, "concert"), "Nirvana",
                valueOf("2022-10-20"));
        int sizeBeforeSaving = eventList.size();

        mockMvc.perform(
                        post("/event")
                                .content(objectMapper.writeValueAsString(event))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Assertions.assertTrue(eventList.size() > sizeBeforeSaving);

    }

    @Test
    public void whenGetEventSuccess() throws Exception {

        Event event = new Event(1L, new EventType(3L, "concert"), "Nirvana",
                valueOf("2022-10-20"));
        eventList.add(event);

        mockMvc.perform(
                        get("/event/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(event)));
    }

    @Test
    public void whenGetEventFailed() {
        final NestedServletException exception = Assertions.assertThrows(NestedServletException.class, () ->
                mockMvc.perform(
                        get("/event/{id}", 1L)));

        Assertions.assertEquals(IllegalStateException.class, exception.getCause().getClass());
    }

}