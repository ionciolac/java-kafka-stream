package ionciolac.kafka.stream.producer;

import ionciolac.kafka.stream.producer.config.KafkaProducerConfig;
import ionciolac.kafka.stream.producer.service.ProducerService;

public class WikimediaChangesProducer {
    public static void main(String[] args) {
        new ProducerService(new KafkaProducerConfig()).start();
    }
}