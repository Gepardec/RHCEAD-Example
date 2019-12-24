package com.gepardec.examples.rhcead.rest;

import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@ApplicationScoped
@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

    @Inject
    private Logger log;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(PersistenceException e) {
        log.error(String.format("Persistence error on uri: '%s'", uriInfo.getPath()), e);
        return Response.serverError().entity("Sorry, a database error occurred").build();
    }
}
