package org.ddd.fundamental.app.service.beachmark;

import com.rabbitmq.client.ConnectionFactory;
import org.ddd.fundamental.app.beachmark.ConnectionPerChannelPublisher;
import org.ddd.fundamental.app.beachmark.SharedConnectionPublisher;
import org.ddd.fundamental.app.beachmark.SingleConnectionPublisher;
import org.ddd.fundamental.app.beachmark.SingleConnectionPublisherNio;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.infrastructure.config.Parameter;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BeachMarkService {

    private static final Logger log = LoggerFactory.getLogger(BeachMarkService.class);

    @Autowired
    private Parameter config;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SharedConnectionPublisher sharedConnectionPublisher;

    @Transactional(transactionManager = "transactionManager")
    public void connectionPerChannel(int workerCount, int iterations, int payloadSize){
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setHost(config.get("RABBITMQ_HOST"));
            factory.setPort(config.getInt("RABBITMQ_PORT"));
            factory.setUsername(config.get("RABBITMQ_LOGIN"));
            factory.setPassword(config.get("RABBITMQ_PASSWORD"));
        } catch (ParameterNotExist e){
            log.error("error,{}", e.getStackTrace());
        }

        LongSummaryStatistics summary = IntStream.range(0, 9)
                .mapToObj(idx -> new ConnectionPerChannelPublisher(factory, workerCount, iterations, payloadSize))
                .map(p -> p.call())
                .collect(Collectors.summarizingLong((l) -> l));

        log.info("[I66] workers={}, throughput={}", workerCount, (int)Math.floor(summary.getAverage()));
    }

    @Transactional(transactionManager = "transactionManager")
    public void sharedConnection(int workerCount, int iterations, int payloadSize) {
        sharedConnectionPublisher.sharedConnection(connectionFactory.getRabbitConnectionFactory(),
                workerCount,iterations,payloadSize,5,5);
    }

    @Transactional(transactionManager = "transactionManager")
    public void singleConnection(int workerCount, int iterations, int payloadSize) {
        LongSummaryStatistics summary = IntStream.range(0, 9)
                .mapToObj(idx -> new SingleConnectionPublisher(connectionFactory.getRabbitConnectionFactory(), workerCount, iterations, payloadSize))
                .map(p -> p.call())
                .collect(Collectors.summarizingLong((l) -> l));

        log.info("[I66] workers={}, throughput={}", workerCount, (int)Math.floor(summary.getAverage()));
    }

    @Transactional(transactionManager = "transactionManager")
    public void singleConnectionNio(int workerCount, int iterations, int payloadSize) {
        LongSummaryStatistics summary = IntStream.range(0, 9)
                .mapToObj(idx -> new SingleConnectionPublisherNio(connectionFactory.getRabbitConnectionFactory(), workerCount, iterations, payloadSize))
                .map(p -> p.call())
                .collect(Collectors.summarizingLong((l) -> l));

        log.info("[I66] workers={}, throughput={}", workerCount, (int)Math.floor(summary.getAverage()));
    }
}
