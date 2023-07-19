package org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq;

import org.junit.Assert;
import org.junit.Test;

public class RabbitMqExchangeNameFormatterTest {

    @Test
    public void testRetry() {
        Assert.assertEquals("retry-post",RabbitMqExchangeNameFormatter.retry("post"));
    }

    @Test
    public void testDeadLetter() {
        Assert.assertEquals("dead_letter-post",RabbitMqExchangeNameFormatter.deadLetter("post"));
    }
}
