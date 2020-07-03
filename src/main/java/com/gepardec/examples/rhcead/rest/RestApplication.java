package com.gepardec.examples.rhcead.rest;

import org.slf4j.MDC;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@ApplicationPath("/")
public class RestApplication extends Application {

    public Runnable populateMDC() {
        final Map<String, String> map = MDC.getCopyOfContextMap();
        return () -> map.forEach(MDC::put);
    }
}
