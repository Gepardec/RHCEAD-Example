package com.gepardec.examples.rhcead.rest;

import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@ApplicationScoped
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    @Inject
    private Logger log;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(Exception e) {
        log.error(String.format("An unhandled error on uri: '%s'", uriInfo.getPath()), e);
        return Response.serverError().entity("Sorry, an unexpected error occurred").build();
    }
}
