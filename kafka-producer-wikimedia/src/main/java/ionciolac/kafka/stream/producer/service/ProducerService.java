package ionciolac.kafka.stream.producer.service;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import ionciolac.kafka.stream.producer.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static ionciolac.kafka.stream.producer.config.ProducerConstants.TOPIC;
import static ionciolac.kafka.stream.producer.config.ProducerConstants.WIKIMEDIA_URL;

public class ProducerService {

    private final Logger log = LoggerFactory.getLogger(ProducerService.class.getSimpleName());
    private final KafkaProducerConfig kafkaProducerConfig;

    public ProducerService(KafkaProducerConfig kafkaProducerConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
    }

    public void start() {
        var kafkaProducer = kafkaProducerConfig.getKafkaProducer();
        var messages = downloadMessages();
        while (messages.iterator().hasNext()) {
            messages.forEach(messageEvent -> sendMessage(kafkaProducer, messageEvent));
        }
    }

    private EventSource getEventSource() {
        return new EventSource.Builder(URI.create(WIKIMEDIA_URL)).build();
    }

    private Iterable<MessageEvent> downloadMessages() {
        return getEventSource().messages();
    }

    private void sendMessage(KafkaProducer<String, String> kafkaProducer, MessageEvent messageEvent) {
        kafkaProducer.send(new ProducerRecord<>(TOPIC, messageEvent.getData()));
        log.info("New message was sent to kafka: " + messageEvent.getData());
    }
}
