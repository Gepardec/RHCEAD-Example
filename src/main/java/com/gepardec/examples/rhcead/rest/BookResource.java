package com.gepardec.examples.rhcead.rest;

import com.gepardec.examples.rhcead.dto.BookDto;
import com.gepardec.examples.rhcead.ejb.BookService;
import org.apache.http.HttpStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@RequestScoped
@Path("/book")
public class BookResource {

    @Inject
    private BookService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok(service.list()).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response get(@Min(value = 0, message = "{id.min}") @PathParam("id") long id) {
        final BookDto bookDto = service.byId(id);
        if (bookDto == null) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("Book with id '%d' not found", id)).build();
        }
        return Response.ok(bookDto).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid @NotNull(message = "{rest.book.null}") BookDto bookDto) {
        return Response.ok(service.createOrUpdate(bookDto)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Min(value = 0, message = "{id.min}") @PathParam("id") long id,
                           @Valid @NotNull(message = "{rest.book.null}") BookDto bookDto) {
        bookDto.setId(id);
        return Response.ok(service.createOrUpdate(bookDto)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(@Min(value = 0, message = "{id.min}") @PathParam("id") long id) {
        if (service.delete(id)) {
            return Response.ok().build();
        }
        return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("Book with id '%d' not found", id)).build();
    }
}
