package com.gepardec.examples.rhcead.rest;

import com.gepardec.examples.rhcead.dto.RoleDto;
import com.gepardec.examples.rhcead.dto.UserDto;
import com.gepardec.examples.rhcead.ejb.UserService;
import org.apache.http.HttpStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * This is a rest resource protected via '@RolesAllowed' annotation which only works on EJBs.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 1/1/2020
 */
@RequestScoped
@Path("/user")
public class UserResource {

    @Inject
    private UserService service;

    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response list() {
        return Response.ok(service.list()).build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response byId(@PathParam("id")
                         @NotNull(message = "{id.notnull}")
                         @Min(value = 1, message = "{id.min}") final Long id) {
        final UserDto userDto = service.byId(id);
        if (userDto == null) {
            return buildNotFoundResponse(id);
        }

        return Response.ok(userDto).build();
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response create(@Valid
                           @NotNull UserDto userDto) {
        userDto.setId(null);
        final UserDto result = service.createOrUpdate(userDto);
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response update(@PathParam("id")
                           @Min(value = 0, message = "{id.min}") long id,
                           @Valid
                           @NotNull UserDto userDto) {
        userDto.setId(id);
        final UserDto updatedDto = service.createOrUpdate(userDto);
        if (updatedDto == null) {
            return buildNotFoundResponse(id);
        }

        return Response.ok(updatedDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response delete(@PathParam("id")
                           @Min(value = 0, message = "{id.min}") long id) {
        if (service.delete(id)) {
            return Response.ok().build();
        }
        return buildNotFoundResponse(id);

    }

    // List parameter doesn't work because of
    // https://issues.redhat.com/browse/WFLY-11566?focusedCommentId=13679356&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel&_sscc=t
    @PUT
    @Path("/{id}/role")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response assignRoles(@PathParam("id")
                                @Min(value = 0, message = "{id.min}") long id,
                                @FormParam("role")
                                @NotNull RoleDto role) {
        final UserDto userDto = service.assignRoles(id, Collections.singletonList(role));
        if (userDto == null) {
            return buildNotFoundResponse(id);
        }

        return Response.ok(userDto).build();
    }

    private Response buildNotFoundResponse(final long id) {
        return Response.status(HttpStatus.SC_NOT_FOUND).entity(String.format("User with id '%d' not found", id)).build();
    }
}
