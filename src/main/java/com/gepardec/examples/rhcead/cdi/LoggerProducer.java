package com.gepardec.examples.rhcead.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@ApplicationScoped
public class LoggerProducer {

    @Produces
    @Dependent
    Logger createLogger(final InjectionPoint ip) {
        if (ip.getMember() != null) {
            return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
        } else if (ip.getBean() != null) {
            return LoggerFactory.getLogger(ip.getBean().getBeanClass());
        } else {
            return LoggerFactory.getLogger("default");
        }
    }
}
