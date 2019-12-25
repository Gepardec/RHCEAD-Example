package com.gepardec.examples.rhcead.jms;

import com.gepardec.examples.rhcead.dto.BookDto;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/25/2019
 */
@RequestScoped
public class BookNotifier {

    @Inject
    private Logger log;

    // The queue we send the notification to
    @Resource(name = "java:jboss/exported/jms/queue/book")
    private Queue queue;

    // The JMSContext used to create the producer which sends the message
    @Inject
    private JMSContext jmxCtx;

    public void sendBookCreated(final BookDto bookDto) {
        send(bookDto, "Create book notification. id '%d'");
    }

    public void sendBookUpdated(final BookDto bookDto) {
        send(bookDto, "Updated book notification. id '%d'");
    }

    public void sendBookDeleted(final BookDto bookDto) {
        send(bookDto, "Delete book notification. id '%d'");
    }

    private void send(final BookDto book, final String messageTemplate) {
        try {
            final String message = String.format(messageTemplate, book.getId());
            final JMSMessage jmsMessage = new JMSMessage(message, book);
            jmxCtx.createProducer().send(queue, jmsMessage);
            log.info(message);
        } catch (Exception e) {
            log.error("Notifying book creation failed", e);
        }
    }
}
