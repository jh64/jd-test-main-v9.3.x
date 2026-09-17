package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JdTestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + 2 * Short.MAX_VALUE);

    public static JdTest getJdTestSample1() {
        return new JdTest()
            .id(1L)
            .name("name1")
            .classification("classification1")
            .nodeId(1L)
            .node("node1")
            .code(1)
            .codeDescription("codeDescription1");
    }

    public static JdTest getJdTestSample2() {
        return new JdTest()
            .id(2L)
            .name("name2")
            .classification("classification2")
            .nodeId(2L)
            .node("node2")
            .code(2)
            .codeDescription("codeDescription2");
    }

    public static JdTest getJdTestRandomSampleGenerator() {
        return new JdTest()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .classification(UUID.randomUUID().toString())
            .nodeId(longCount.incrementAndGet())
            .node(UUID.randomUUID().toString())
            .code(intCount.incrementAndGet())
            .codeDescription(UUID.randomUUID().toString());
    }
}
