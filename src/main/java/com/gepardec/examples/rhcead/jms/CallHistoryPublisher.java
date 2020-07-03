package com.gepardec.examples.rhcead.jms;

import com.gepardec.examples.rhcead.jpa.Book_;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/29/2019
 */
@RequestScoped
public class CallHistoryPublisher {

    @Resource(lookup = "java:global/jms/topics/callHistory")
    private Topic topic;

    @Inject
    private JMSContext jmsContext;

    @Inject
    private Logger log;

    public void publish(final String resourceName) {
        try {
            jmsContext.createProducer().send(topic, new JMSMessage("CalledNotification", resourceName));
            log.info("Published to topic '{}'", topic.getTopicName());
        } catch (Exception e) {
            log.error("Could not publish to topic", e);
        }
    }
}
