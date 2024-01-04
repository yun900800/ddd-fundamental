package org.ddd.fundamental.message.kafka.contest;

import org.ddd.fundamental.message.kafka.contest.KafkaAdminClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class KafkaAdminClientTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
    private KafkaAdminClient kafkaAdminClient;

    @BeforeEach
    void setup() {
        KAFKA_CONTAINER.addExposedPort(9092);
        this.kafkaAdminClient = new KafkaAdminClient(KAFKA_CONTAINER.getBootstrapServers());
    }

    @AfterEach
    void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenKafkaIsRunning_whenCheckedForConnection_thenConnectionIsVerified() throws Exception {
        boolean alive = kafkaAdminClient.verifyConnection();
        assertThat(alive).isTrue();
    }
}
