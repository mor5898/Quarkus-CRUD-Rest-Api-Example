package de.benevolo;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;

@Path("/event")
public class EventResource {
    @Inject
    EventRepository eventRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Event postEvent(Event event){
        eventRepository.persist(event);
        return event;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> events() {
        Event event = new Event();
        event.setName("Event 1");
        event.setDescription("Test Description");
        event.setDate(new Date());
        return List.of(event);
    }
}
