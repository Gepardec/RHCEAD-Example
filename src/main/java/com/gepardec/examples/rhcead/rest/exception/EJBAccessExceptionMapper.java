package com.gepardec.examples.rhcead.rest.exception;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;

import javax.ejb.EJBAccessException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@ApplicationScoped
@Provider
public class EJBAccessExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<EJBAccessException> {

    @Inject
    private Logger log;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Override
    public Response toResponse(final EJBAccessException e) {
        log.error(String.format("User '{}' not permitted to access resources: '%s'",
                securityContext.getUserPrincipal().getName(),
                uriInfo.getPath()), e);
        return Response.status(HttpStatus.SC_FORBIDDEN).type(MediaType.TEXT_PLAIN_TYPE).entity("Sorry, you are not permitted to access the resource").build();
    }
}
