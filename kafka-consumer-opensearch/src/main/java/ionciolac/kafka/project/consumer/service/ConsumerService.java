package ionciolac.kafka.project.consumer.service;

import com.google.gson.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.index.IndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ionciolac.kafka.project.consumer.config.ConsumerConstants.OPEN_SEARCH_INDEX_NAME;
import static ionciolac.kafka.project.consumer.config.ConsumerConstants.TOPIC;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singleton;
import static org.opensearch.common.xcontent.XContentType.JSON;

public class ConsumerService {

    private final Logger log = LoggerFactory.getLogger(ConsumerService.class.getSimpleName());
    private final KafkaConsumerService kafkaConsumerService;
    private final OpenSearchService openSearchService;

    public ConsumerService(KafkaConsumerService kafkaConsumerService, OpenSearchService openSearchService) {
        this.kafkaConsumerService = kafkaConsumerService;
        this.openSearchService = openSearchService;
    }

    public void startConsume() {
        var kafkaConsumer = kafkaConsumerService.getKafkaConsumer();
        kafkaConsumer.subscribe(singleton(TOPIC));
        var openSearchClient = openSearchService.connectionToOpenSearch();
        openSearchService.createIndex(openSearchClient);

        while (true) {
            var bulkRequest = new BulkRequest();
            var records = kafkaConsumer.poll(ofMillis(3000));
            for (ConsumerRecord<String, String> record : records) {
                var id = extractId(record.value());
                var indexRequest = new IndexRequest(OPEN_SEARCH_INDEX_NAME).source(record.value(), JSON).id(id);
                bulkRequest.add(indexRequest);
                if (bulkRequest.numberOfActions() > 0) {
                    openSearchService.bulkInsert(openSearchClient, bulkRequest);
                    // commit offsets after the batch is consumed
                    kafkaConsumer.commitSync();
                    log.info("Offsets have been committed!");
                }
            }
        }
    }

    private static String extractId(String json) {
        return JsonParser.parseString(json)
                .getAsJsonObject()
                .get("meta")
                .getAsJsonObject()
                .get("id")
                .getAsString();
    }
}
