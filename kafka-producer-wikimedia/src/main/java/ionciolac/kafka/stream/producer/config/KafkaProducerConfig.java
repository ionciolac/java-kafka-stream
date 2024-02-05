package ionciolac.kafka.stream.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static ionciolac.kafka.stream.producer.config.ProducerConstants.BOOTSTRAP_SERVERS_URL;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaProducerConfig {

    public KafkaProducer<String, String> getKafkaProducer() {
        // setup kafka properties
        var properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // create kafka producer
        return new KafkaProducer<>(properties);
    }
}
