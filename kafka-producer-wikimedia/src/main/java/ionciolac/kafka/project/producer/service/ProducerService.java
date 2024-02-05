package ionciolac.kafka.project.producer.service;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static ionciolac.kafka.project.producer.config.ProducerConstants.TOPIC;
import static ionciolac.kafka.project.producer.config.ProducerConstants.WIKIMEDIA_URL;

public class ProducerService {

    private final Logger log = LoggerFactory.getLogger(ProducerService.class.getSimpleName());
    private final KafkaProducerService kafkaProducerService;

    public ProducerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public void start() {
        var kafkaProducer = kafkaProducerService.getKafkaProducer();
        var messages = downloadMessages();
        while (messages.iterator().hasNext()) {
            messages.forEach(messageEvent -> {
                kafkaProducerService.sendMessage(kafkaProducer, TOPIC, messageEvent);
                log.info("New message was sent to kafka: " + messageEvent.getData());
            });
        }
    }

    private EventSource getEventSource() {
        return new EventSource.Builder(URI.create(WIKIMEDIA_URL)).build();
    }

    private Iterable<MessageEvent> downloadMessages() {
        return getEventSource().messages();
    }
}
