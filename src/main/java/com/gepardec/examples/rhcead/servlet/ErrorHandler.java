package com.gepardec.examples.rhcead.servlet;

import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles Servlet errors which are not handled by JAX-RS, such as 'security-constraint' violations.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 1/1/2020
 */
@WebServlet(name = "errorHandler", urlPatterns = "/error")
public class ErrorHandler extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (HttpStatus.SC_NOT_FOUND == resp.getStatus()) {
            resp.getWriter().print("Sorry, this resource could not be found");
        } else if (HttpStatus.SC_FORBIDDEN == resp.getStatus()) {
            resp.getWriter().print("Sorry, this resource is forbidden");
        } else {
            resp.getWriter().print("Sorry, an error happened on our side");
        }
    }
}
