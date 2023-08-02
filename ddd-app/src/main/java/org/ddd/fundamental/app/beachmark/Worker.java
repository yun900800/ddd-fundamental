package org.ddd.fundamental.app.beachmark;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 一个worker对应一个channel,为了测试吞吐量而迭代次数
 */
public class Worker implements Callable<Worker.WorkerResult> {

    private final Channel channel;
    private int iterations;
    private final CountDownLatch counter;
    private final String queue;
    private final byte[] payload;

    Worker(String queue, Connection conn, int iterations,
           CountDownLatch counter, int payloadSize) throws IOException {
        this.iterations = iterations;
        this.counter = counter;
        this.queue = queue;

        channel = conn.createChannel();
        //先删除队列,然后新增一个队列
        channel.queueDelete(queue);
        channel.queueDeclare(queue, false, false, true, null);

        this.payload = new byte[payloadSize];
        new Random().nextBytes(payload);
    }

    @Override
    public WorkerResult call() throws Exception {
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < iterations; i++) {
                channel.basicPublish("", queue, null, payload);
            }

            long elapsed = System.currentTimeMillis() - start;
            //数据发送完成后删除队列
            channel.queueDelete(queue);
            return new WorkerResult(elapsed);
        } finally {
            counter.countDown();
        }
    }


    public static class WorkerResult {
        public final long elapsed;

        WorkerResult(long elapsed) {
            this.elapsed = elapsed;
        }
    }
}
