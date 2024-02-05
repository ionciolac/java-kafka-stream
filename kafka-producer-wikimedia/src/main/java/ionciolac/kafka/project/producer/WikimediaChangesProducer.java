package ionciolac.kafka.project.producer;

import ionciolac.kafka.project.producer.service.KafkaProducerService;
import ionciolac.kafka.project.producer.service.ProducerService;

public class WikimediaChangesProducer {
    public static void main(String[] args) {
        new ProducerService(new KafkaProducerService()).start();
    }
}