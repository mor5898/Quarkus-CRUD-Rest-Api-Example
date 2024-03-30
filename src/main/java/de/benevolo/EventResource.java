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
        return eventRepository.listAll();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        if (!eventRepository.deleteById(id)) {
            throw new WebApplicationException(404);
        } else {
            eventRepository.deleteById(id);
        }
    }
}
