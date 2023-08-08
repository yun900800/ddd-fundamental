package org.ddd.fundamental.app.beachmark;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConnectionPerChannelPublisher implements Callable<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPerChannelPublisher.class);
    private final ConnectionFactory factory;
    private final int workerCount;
    private final int iterations;
    private final int payloadSize;

    public ConnectionPerChannelPublisher(ConnectionFactory factory, int workerCount, int iterations, int payloadSize) {
        this.factory = factory;
        this.workerCount = workerCount;
        this.iterations = iterations;
        this.payloadSize = payloadSize;
    }

    @Override
    public Long call() {
        try {
            List<Worker> workers = new ArrayList<>();
            CountDownLatch counter = new CountDownLatch(workerCount);

            //每个工作worker一个连接
            for (int i = 0; i < workerCount; i++) {
                Connection conn = factory.newConnection();
                workers.add(new Worker("queue_" + i, conn, iterations, counter, payloadSize));
            }

            ExecutorService executor = new ThreadPoolExecutor(workerCount, workerCount,
                    0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(workerCount, true));
            long start = System.currentTimeMillis();
            LOGGER.info("[I61] Starting {} workers...", workers.size());
            executor.invokeAll(workers);
            if (counter.await(5, TimeUnit.MINUTES)) {
                long elapsed = System.currentTimeMillis() - start;
                LOGGER.info("[I59] Tasks completed: #workers={}, #iterations={}, elapsed={}ms, stats={}", workerCount, iterations, elapsed);
                return throughput(workerCount, iterations, elapsed);
            } else {
                throw new RuntimeException("[E61] Timeout waiting workers to complete");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static long throughput(int workerCount, int iterations, long elapsed) {
        return (iterations * workerCount * 1000) / elapsed;
    }
}
