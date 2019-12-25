package com.gepardec.examples.rhcead.rest.exception;

import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
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
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    private Logger log;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(WebApplicationException e) {
        log.warn(String.format("WebApplicationException thrown on status '%s', uri '%s'", e.getResponse().getStatus(), uriInfo.getPath()));
        return e.getResponse();
    }
}
