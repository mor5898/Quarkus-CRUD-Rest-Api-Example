package de.benevolo;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @Transactional
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") long id, Event event) {
        if (eventRepository.findById(id) == null) {
            eventRepository.persist(event);
            return Response.status(201).build();
        } else {
            Event eventToChange = eventRepository.findById(id);
            eventToChange.setDate(event.getDate());
            eventToChange.setName(event.getName());
            eventToChange.setDescription(event.getDescription());
            eventRepository.persist(eventToChange);
            return Response.status(205).build();
        }
    }
}
