package com.gepardec.examples.rhcead.cdi;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple application scoped cache for metrics.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/29/2019
 */
@ApplicationScoped
public class CallHistoryMetric {

    private Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    public Integer incrementAndGet(final String mapKey) {
        return counters.computeIfAbsent(mapKey, (key) -> new AtomicInteger(0)).incrementAndGet();
    }
}
