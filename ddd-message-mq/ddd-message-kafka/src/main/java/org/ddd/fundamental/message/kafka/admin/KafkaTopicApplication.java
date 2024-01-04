package org.ddd.fundamental.message.kafka.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.TopicConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaTopicApplication {

    private final Properties properties;

    public KafkaTopicApplication(Properties properties){
        this.properties = properties;
    }

    public void createTopic(String topicName){
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

            // get the async result for the new topic creation
            KafkaFuture<Void> future = result.values()
                    .get(topicName);

            // call get() to block until topic creation has completed or failed
            future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建一个带有可选参数的admin
     * @param topicName
     * @throws Exception
     */
    public void createTopicWithOptions(String topicName)  {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            CreateTopicsOptions topicOptions = new CreateTopicsOptions().validateOnly(true)
                    .retryOnQuotaViolation(true);

            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic), topicOptions);

            KafkaFuture<Void> future = result.values()
                    .get(topicName);
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void createCompactedTopicWithCompression(String topicName) throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;

            // Create a compacted topic with 'lz4' compression codec
            Map<String, String> newTopicConfig = new HashMap<>();
            newTopicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT);
            newTopicConfig.put(TopicConfig.COMPRESSION_TYPE_CONFIG, "lz4");
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor).configs(newTopicConfig);

            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

            KafkaFuture<Void> future = result.values()
                    .get(topicName);
            future.get();
        }
    }



}
