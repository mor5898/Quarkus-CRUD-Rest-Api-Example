package de.benevolo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;

@Path("/event")
public class EventResource {

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
