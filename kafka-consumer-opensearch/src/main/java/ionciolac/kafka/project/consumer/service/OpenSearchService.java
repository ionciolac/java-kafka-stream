package ionciolac.kafka.project.consumer.service;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

import static ionciolac.kafka.project.consumer.config.ConsumerConstants.OPEN_SEARCH_INDEX_NAME;
import static ionciolac.kafka.project.consumer.config.ConsumerConstants.OPEN_SEARCH_URL;
import static org.opensearch.client.RequestOptions.DEFAULT;

public class OpenSearchService {

    private final Logger log = LoggerFactory.getLogger(OpenSearchService.class.getSimpleName());

    public RestHighLevelClient connectionToOpenSearch() {
        // we build a URI from the connection string
        var connUri = URI.create(OPEN_SEARCH_URL);
        // extract login information if it exists
        var userInfo = connUri.getUserInfo();
        var host = connUri.getHost();
        var port = connUri.getPort();
        if (userInfo == null) {
            // REST client without security
            return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
        } else {
            // REST client with security
            var auth = userInfo.split(":");
            var cp = new BasicCredentialsProvider();
            cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth[0], auth[1]));
            return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, connUri.getScheme()))
                    .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                            httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                    .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));
        }
    }

    public void createIndex(RestHighLevelClient openSearchClient) {
        try {
            var indexExists = openSearchClient
                    .indices().exists(new GetIndexRequest(OPEN_SEARCH_INDEX_NAME), DEFAULT);
            if (indexExists) {
                log.info("The Wikimedia Index already exits");
            } else {
                var createIndexRequest = new CreateIndexRequest(OPEN_SEARCH_INDEX_NAME);
                openSearchClient.indices().create(createIndexRequest, DEFAULT);
                log.info("The Wikimedia Index has been created!");
            }
        } catch (IOException e) {
            log.error("The Wikimedia Index, can't be created. Because of exception: " + e);
        }
    }

    public void bulkInsert(RestHighLevelClient openSearchClient, BulkRequest bulkRequest) {
        try {
            var bulkResponse = openSearchClient.bulk(bulkRequest, DEFAULT);
            log.info("Inserted " + bulkResponse.getItems().length + " record(s).");
        } catch (IOException e) {
            log.error("Bulk insert can't be executed because of Exception: " + e);
        }
    }
}
