package com.gepardec.examples.rhcead.jms;

import java.io.Serializable;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/25/2019
 */
public class JMSMessage implements Serializable {

    private final String message;
    private final Object data;

    public JMSMessage(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public <T> T getData(Class<T> clazz) {
        return (T) data;
    }

    public String getMessage() {
        return message;
    }
}
