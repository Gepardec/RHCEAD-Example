package com.gepardec.examples.rhcead.rest;

import com.gepardec.examples.rhcead.dto.BookDto;
import com.gepardec.examples.rhcead.ejb.BookService;
import com.gepardec.examples.rhcead.jms.BookNotifier;
import org.apache.http.HttpStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@RequestScoped
@Path("/book")
public class BookResource {

    @Inject
    private BookService service;

    @Inject
    private BookNotifier notifier;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok(service.list()).build();
    }

    @GET
    @Path("/search/name")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byName(@Size(min = 1, max = 255, message = "{name.size}")
                           @NotNull(message = "{name.null}")
                           @QueryParam("value") final String name) {
        final List<BookDto> result = service.searchByName(name);
        return Response.ok(result).build();
    }

    @GET
    @Path("/search/library")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byLibraryId(@Min(value = 1, message = "{libraryId.min}")
                                @NotNull(message = "{libraryId.null}")
                                @QueryParam("value") final Long id) {
        final List<BookDto> result = service.searchByLibraryId(id);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byId(@Min(value = 0, message = "{id.min}")
                         @PathParam("id") long id) {
        final BookDto bookDto = service.byId(id);
        if (bookDto == null) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("Book with id '%d' not found", id)).build();
        }
        return Response.ok(bookDto).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@NotNull(message = "{rest.book.null}")
                           @Valid BookDto bookDto) {
        bookDto.setId(null);
        final BookDto result = service.createOrUpdate(bookDto);
        notifier.sendBookCreated(result);
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Min(value = 0, message = "{id.min}")
                           @PathParam("id") long id,
                           @NotNull(message = "{rest.book.null}")
                           @Valid BookDto bookDto) {
        bookDto.setId(id);
        final BookDto updatedDto = service.createOrUpdate(bookDto);
        if (updatedDto == null) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("Book with id '%d' not found", id)).build();
        } else {
            notifier.sendBookUpdated(bookDto);
        }
        return Response.ok(updatedDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(@Min(value = 0, message = "{id.min}")
                           @PathParam("id") long id) {
        if (service.delete(id)) {
            final BookDto bookDto = new BookDto();
            bookDto.setId(id);
            notifier.sendBookDeleted(bookDto);
            return Response.ok().build();
        }
        return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("Book with id '%d' not found", id)).build();
    }
}
