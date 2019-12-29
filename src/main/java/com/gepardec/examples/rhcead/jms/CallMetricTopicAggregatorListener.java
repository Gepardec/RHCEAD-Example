package com.gepardec.examples.rhcead.jms;

import com.gepardec.examples.rhcead.cdi.CallHistoryMetric;
import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/29/2019
 */
@ApplicationScoped
// Configures the topic which is created by the container and used by the application
// Need to use 'global' namespace because with 'app' namespace MDB weren't called
@JMSDestinationDefinition(
        name = "java:global/jms/topics/callHistory",
        description = "Notifies clients about a rest call on any rest interface",
        destinationName = "CallHistoryTopic",
        interfaceName = "javax.jms.Topic"
)
// Subscribe to the topic
@MessageDriven(activationConfig = {
        // Where we receive message from
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:global/jms/topics/callHistory"),
        // The type the destination where we receive message from
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "subscriptionName",
                propertyValue = "CallMetricTopicAggregatorListener"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability",
                propertyValue = "Durable")
})
public class CallMetricTopicAggregatorListener implements MessageListener {

    @Inject
    private Logger log;

    @Inject
    private CallHistoryMetric callHistoryMetric;

    @Override
    public void onMessage(Message message) {
        try {
            final JMSMessage receivedMessage = message.getBody(JMSMessage.class);
            final String resourceName = receivedMessage.getData(String.class);
            final Integer currentCount = callHistoryMetric.incrementAndGet(resourceName);
            log.info(receivedMessage.getMessage() + " | resourceName '{}' = '{}'", resourceName, currentCount);
            message.acknowledge();
        } catch (JMSException e) {
            log.error("Could not handle received message", e);
        }
    }
}
