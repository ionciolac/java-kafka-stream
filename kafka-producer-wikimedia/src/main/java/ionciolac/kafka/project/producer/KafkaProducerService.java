package ionciolac.kafka.project.producer;

import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static ionciolac.kafka.project.producer.ProducerConstants.BOOTSTRAP_SERVERS_URL;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaProducerService {

    public KafkaProducer<String, String> getKafkaProducer() {
        // setup kafka properties
        var properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // create kafka producer
        return new KafkaProducer<>(properties);
    }

    public void sendMessage(KafkaProducer<String, String> kafkaProducer, String topic,
                            MessageEvent messageEvent) {
        kafkaProducer.send(new ProducerRecord<>(topic, messageEvent.getData()));
    }
}
