package org.ddd.fundamental.app.beachmark;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SingleConnectionPublisherNio implements Callable<Long>{

    private static final Logger log = LoggerFactory.getLogger(SingleConnectionPublisherNio.class);

    private final ConnectionFactory factory;
    private final int workerCount;
    private final int iterations;
    private final int payloadSize;

    public SingleConnectionPublisherNio(ConnectionFactory factory, int workerCount, int iterations, int payloadSize) {
        this.factory = factory;
        this.workerCount = workerCount;
        this.iterations = iterations;
        this.payloadSize = payloadSize;
    }

    @Override
    public Long call() {
        try {
            factory.useNio();
            Connection connection = factory.newConnection();

            List<Worker> workers = new ArrayList<>();

            log.info("[I35] Creating {} worker{}...", workerCount, (workerCount > 1)?"s":"");

            CountDownLatch counter = new CountDownLatch(workerCount);

            for( int i = 0 ; i < workerCount ; i++ ) {
                workers.add(new Worker("queue_" + i, connection, iterations, counter,payloadSize));
            }

            ExecutorService executor = new ThreadPoolExecutor(workerCount, workerCount, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(workerCount, true));
            long start = System.currentTimeMillis();
            log.info("[I61] Starting workers...");
            List<Future<WorkerResult>> results = executor.invokeAll(workers);

            log.info("[I55] Waiting workers to complete...");
            if( counter.await(5, TimeUnit.MINUTES) ) {
                long elapsed = System.currentTimeMillis() - start;
                log.info("[I59] Tasks completed: #workers={}, #iterations={}, elapsed={}ms",
                        workerCount,
                        iterations,
                        elapsed);

                LongSummaryStatistics summary = results.stream()
                        .map(f -> safeGet(f))
                        .map(r -> r.elapsed)
                        .collect(Collectors.summarizingLong((l) -> l));

                log.info("[I74] stats={}", summary);
                log.info("[I79] result: workers={}, throughput={}",workerCount,throughput(workerCount,iterations,elapsed));
                return throughput(workerCount,iterations,elapsed);
            }
            else {
                log.error("[E61] Timeout waiting workers to complete");
            }

        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private static long throughput(int workerCount, int iterations, long elapsed) {
        return (iterations*workerCount*1000)/elapsed;
    }

    private static <T> T safeGet(Future<T> f) {
        try {
            return f.get();
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class WorkerResult {
        public final long elapsed;
        WorkerResult(long elapsed) {
            this.elapsed = elapsed;
        }
    }


    private static class Worker implements Callable<WorkerResult> {

        private final Connection conn;
        private final Channel channel;
        private int iterations;
        private final CountDownLatch counter;
        private final String queue;
        private final byte[] payload;

        Worker(String queue, Connection conn, int iterations, CountDownLatch counter,int payloadSize) throws IOException {
            this.conn = conn;
            this.iterations = iterations;
            this.counter = counter;
            this.queue = queue;

            channel = conn.createChannel();
            channel.queueDeclare(queue, false, false, true, null);

            this.payload = new byte[payloadSize];
            new Random().nextBytes(payload);

        }

        @Override
        public WorkerResult call() throws Exception {

            try {
                long start = System.currentTimeMillis();
                for ( int i = 0 ; i < iterations ; i++ ) {
                    channel.basicPublish("", queue, null,payload);
                }

                long elapsed = System.currentTimeMillis() - start;
                channel.queueDelete(queue);
                return new WorkerResult(elapsed);
            }
            finally {
                counter.countDown();
            }
        }

    }
}
