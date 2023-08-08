package org.ddd.fundamental.app.beachmark;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SingleConnectionPublisher implements Callable<Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleConnectionPublisher.class);

    private final ConnectionFactory factory;
    private final int workerCount;
    private final int iterations;
    private final int payloadSize;

    public SingleConnectionPublisher(ConnectionFactory factory, int workerCount, int iterations, int payloadSize) {
        this.factory = factory;
        this.workerCount = workerCount;
        this.iterations = iterations;
        this.payloadSize = payloadSize;
    }

    @Override
    public Long call() {

        try {

            Connection connection = factory.newConnection();
            CountDownLatch counter = new CountDownLatch(workerCount);
            List<Worker> workers = new ArrayList<>();

            for( int i = 0 ; i < workerCount ; i++ ) {
                workers.add(new Worker("queue_" + i, connection, iterations, counter,payloadSize));
            }

            ExecutorService executor = new ThreadPoolExecutor(workerCount, workerCount, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(workerCount, true));
            long start = System.currentTimeMillis();
            LOGGER.info("[I61] Starting {} workers...", workers.size());
            List<Future<Worker.WorkerResult>> results = executor.invokeAll(workers);

            if( counter.await(5, TimeUnit.MINUTES) ) {
                long elapsed = System.currentTimeMillis() - start;

                LongSummaryStatistics summary = results.stream()
                        .map(f -> safeGet(f))
                        .map(r -> r.elapsed)
                        .collect(Collectors.summarizingLong((l) -> l));

                LOGGER.info("[I59] Tasks completed: #workers={}, #iterations={}, elapsed={}ms, stats={}",
                        workerCount,
                        iterations,
                        elapsed, summary);

                return throughput(workerCount,iterations,elapsed);
            }
            else {
                throw new RuntimeException("[E61] Timeout waiting workers to complete");
            }
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static <T> T safeGet(Future<T> f) {
        try {
            return f.get();
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static long throughput(int workerCount, int iterations, long elapsed) {
        return (iterations*workerCount*1000)/elapsed;
    }
}
