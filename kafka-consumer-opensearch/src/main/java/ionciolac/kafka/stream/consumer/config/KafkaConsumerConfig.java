package ionciolac.kafka.stream.consumer.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

import static ionciolac.kafka.stream.consumer.config.ConsumerConstants.GROUP_ID;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class KafkaConsumerConfig {

    public KafkaConsumer<String, String> getKafkaConsumer() {
        // setup kafka properties
        var properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, ConsumerConstants.BOOTSTRAP_SERVERS_URL);
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(GROUP_ID_CONFIG, GROUP_ID);
        // create kafka producer
        return new KafkaConsumer<>(properties);
    }
}
