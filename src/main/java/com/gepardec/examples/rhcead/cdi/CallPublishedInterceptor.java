package com.gepardec.examples.rhcead.cdi;

import com.gepardec.examples.rhcead.jms.CallHistoryPublisher;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/29/2019
 */
@Interceptor
@CallPublished
public class CallPublishedInterceptor {

    @Inject
    private CallHistoryPublisher publisher;

    @AroundInvoke
    public Object invoke(final InvocationContext ic) throws Exception {
        try {
            final Object result = ic.proceed();
            final String resourceName = ic.getTarget().getClass().getSuperclass().getSimpleName() + "#" + ic.getMethod().getName();
            publisher.publish(resourceName);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
