package ionciolac.kafka.stream.producer;

import ionciolac.kafka.stream.producer.service.KafkaProducerService;
import ionciolac.kafka.stream.producer.service.ProducerService;

public class WikimediaChangesProducer {
    public static void main(String[] args) {
        new ProducerService(new KafkaProducerService()).start();
    }
}