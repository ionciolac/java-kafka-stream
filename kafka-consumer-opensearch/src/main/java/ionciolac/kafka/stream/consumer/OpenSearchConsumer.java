package ionciolac.kafka.stream.consumer;

import ionciolac.kafka.stream.consumer.service.ConsumerService;
import ionciolac.kafka.stream.consumer.service.KafkaConsumerService;
import ionciolac.kafka.stream.consumer.service.OpenSearchService;

public class OpenSearchConsumer {
    public static void main(String[] args) {
        new ConsumerService(new KafkaConsumerService(), new OpenSearchService()).startConsume();
    }
}