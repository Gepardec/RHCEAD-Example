package com.gepardec.examples.rhcead.rest.exception;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@ApplicationScoped
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Inject
    private Logger log;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException e) {
        log.warn(String.format("Validation failed on uri '%s'", uriInfo.getPath()));
        final List<String> violations = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return Response.status(HttpStatus.SC_BAD_REQUEST).entity(violations).build();
    }
}
