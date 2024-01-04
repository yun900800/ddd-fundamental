package org.ddd.fundamental.message.kafka.admin;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class KafkaTopicApplicationTest {

    //创建一个kafka容器实例
    @Container
    private static final KafkaContainer KAFKA_CONTAINER =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    private KafkaTopicApplication kafkaTopicApplication;


    @BeforeEach
    void setup() {

        //先启动容器,再暴露端口
        KAFKA_CONTAINER.start();
        KAFKA_CONTAINER.addExposedPort(9092);
        //创建客户端
        Properties properties = new Properties();
        String bootstrap = KAFKA_CONTAINER.getBootstrapServers();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        kafkaTopicApplication = new KafkaTopicApplication(properties);
    }

    @Test
    void givenTopicName_whenCreateNewTopic_thenTopicIsCreated() throws Exception {
        kafkaTopicApplication.createTopic("test-topic-with-options");

        String topicCommand = "/usr/bin/kafka-topics --bootstrap-server=localhost:9092 --list";
        String stdout = KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", topicCommand)
                .getStdout();
        assertThat(stdout).contains("test-topic");
    }

    @Test
    void givenTopicName_whenCreateNewTopicWithOptions_thenTopicIsCreated() throws Exception {
        kafkaTopicApplication.createTopicWithOptions("test-topic-with-options");

        String topicCommand = "/usr/bin/kafka-topics --bootstrap-server=localhost:9092 --list";
        String stdout = KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", topicCommand)
                .getStdout();
        assertThat(stdout).doesNotContain("test-topic-with-options");
    }

    @Test
    void givenTopicName_whenCreateNewTopicWithCompression_thenTopicIsCreated() throws Exception {
        kafkaTopicApplication.createCompactedTopicWithCompression("test-topic-with-compression");

        String topicCommand = "/usr/bin/kafka-topics --bootstrap-server=localhost:9092 --list";
        String stdout = KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", topicCommand)
                .getStdout();
        assertThat(stdout).contains("test-topic-with-compression");
    }
}
