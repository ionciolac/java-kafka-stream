package ionciolac.kafka.project.consumer;

import ionciolac.kafka.project.consumer.service.ConsumerService;
import ionciolac.kafka.project.consumer.service.KafkaConsumerService;
import ionciolac.kafka.project.consumer.service.OpenSearchService;

public class OpenSearchConsumer {
    public static void main(String[] args) {
        new ConsumerService(new KafkaConsumerService(), new OpenSearchService()).startConsume();
    }
}