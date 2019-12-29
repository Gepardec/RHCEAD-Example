package com.gepardec.examples.rhcead.jms;

import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/29/2019
 */
@Dependent
@MessageDriven(activationConfig = {
        // Where we receive message from
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:global/jms/topics/callHistory"),
        // The type the destination where we receive message from
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "subscriptionName",
                propertyValue = "CallMetricTopicLoggerListener"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability",
                propertyValue = "Durable")
})
public class CallMetricTopicLoggerListener implements MessageListener {

    @Inject
    private Logger log;

    @Override
    public void onMessage(Message message) {
        try {
            final JMSMessage receivedMessage = message.getBody(JMSMessage.class);
            final String resourceName = receivedMessage.getData(String.class);
            log.info(receivedMessage.getMessage() + " | resourceName '{}'", resourceName);
            message.acknowledge();
        } catch (JMSException e) {
            log.error("Could not handle received message", e);
        }
    }
}
