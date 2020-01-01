package com.gepardec.examples.rhcead.rest;

import com.gepardec.examples.rhcead.cdi.CallPublished;
import com.gepardec.examples.rhcead.dto.BookDto;
import com.gepardec.examples.rhcead.ejb.BookService;
import com.gepardec.examples.rhcead.jms.BookQueueSender;
import org.apache.http.HttpStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * Protected via 'security-constraints' defined in web.xml.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@RequestScoped
@CallPublished
@Path("/book")
public class BookResource {

    @Inject
    private BookService service;

    @Inject
    private BookQueueSender notifier;

    @Context
    private SecurityContext securityContext;

    @Context

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response list() {
        return Response.ok(service.list()).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byId(@PathParam("id")
                         @Min(value = 0, message = "{book.id.min}") long id) {
        final BookDto bookDto = service.byId(id);
        if (bookDto == null) {
            return buildNotFoundResponse(id);
        }
        return Response.ok(bookDto).build();
    }

    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byName(@PathParam("name")
                           @Size(min = 1, max = 255, message = "{name.size}")
                           @NotNull(message = "{name.null}") final String name) {
        final List<BookDto> result = service.searchByName(name);
        return Response.ok(result).build();
    }

    @GET
    @Path("/library/{name}/user")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byLibraryUserName(@PathParam("name") final String name) {
        final List<BookDto> result = service.searchBookByLibraryNameAndUserName(name, securityContext.getUserPrincipal().getName());
        return Response.ok(result).build();
    }


    @GET
    @Path("/library/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byId(@PathParam("id")
                         @Min(value = 1, message = "{library.id.min}")
                         @NotNull(message = "{library.id.notnull}") final Long id) {
        final List<BookDto> result = service.searchByLibraryId(id);
        return Response.ok(result).build();
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response create(@NotNull(message = "{rest.book.null}")
                           @Valid BookDto bookDto) {
        bookDto.setId(null);
        final BookDto result = service.createOrUpdate(bookDto);
        notifier.sendBookCreated(result);
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response update(@PathParam("id")
                           @Min(value = 0, message = "{id.min}") long id,
                           @NotNull(message = "{rest.book.null}")
                           @Valid BookDto bookDto) {
        bookDto.setId(id);
        final BookDto updatedDto = service.createOrUpdate(bookDto);
        if (updatedDto == null) {
            return buildNotFoundResponse(id);
        } else {
            notifier.sendBookUpdated(bookDto);
        }
        return Response.ok(updatedDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response delete(@PathParam("id")
                           @Min(value = 0, message = "{id.min}") long id) {
        if (service.delete(id)) {
            final BookDto bookDto = new BookDto();
            bookDto.setId(id);
            notifier.sendBookDeleted(bookDto);
            return Response.ok().build();
        }
        return buildNotFoundResponse(id);
    }

    private Response buildNotFoundResponse(final long id) {
        return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("Book with id '%d' not found", id)).build();
    }
}
