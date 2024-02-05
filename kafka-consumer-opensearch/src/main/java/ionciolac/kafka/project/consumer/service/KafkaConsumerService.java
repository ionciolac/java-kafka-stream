package ionciolac.kafka.project.consumer.service;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

import static ionciolac.kafka.project.consumer.config.ConsumerConstants.BOOTSTRAP_SERVERS_URL;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class KafkaConsumerService {

    public KafkaConsumer<String, String> getKafkaConsumer() {
        String groupId = "consumer-opensearch-demo";
        // setup kafka properties
        var properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(GROUP_ID_CONFIG, groupId);
        // create kafka producer
        return new KafkaConsumer<>(properties);
    }
}
