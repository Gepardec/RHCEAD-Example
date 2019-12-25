package com.gepardec.examples.rhcead.jms;

import com.gepardec.examples.rhcead.dto.BookDto;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
// CDI needs an scope because of used discovery-method annotated.
@Dependent
@MessageDriven(activationConfig = {
        // Where we receive message from
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:jboss/exported/jms/queue/book"),
        // The type the destination where we receive message from
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
})
public class BookQueueListener implements MessageListener {

    @Inject
    private Logger log;

    @Resource
    private MessageDrivenContext ctx;

    @Override
    public void onMessage(Message message) {
        try {
            final JMSMessage receivedMessage = message.getBody(JMSMessage.class);
            final BookDto bookDto = receivedMessage.getData(BookDto.class);
            log.info(receivedMessage.getMessage() + " | id '{}'", bookDto.getId());
        } catch (JMSException e) {
            log.error("Could not handle received message", e);
            ctx.setRollbackOnly();
        }
    }
}
