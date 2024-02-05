# java-kafka-stream
# steps for run kafka-producer-wikimedia
1. Install Kafka on your localhost
2. Start Zookeeper using command "/usr/local/bin/zookeeper-server-start /usr/local/etc/zookeeper/zoo.cfg"
3. Start Apache Kafka using command "/usr/local/bin/kafka-server-start /usr/local/etc/kafka/server.properties"
4. Create new Kafka topic using command "kafka-topics --bootstrap-server localhost:9092 --create --topic wikimedia-recent-changes -- prartions 5"
5. Now you can run WikimediaChangesProducer

# steps for run kafka-consumer-opensearch
1. Install docker on your localhost
2. Run "services" from docker-compose.yml file from kafka-consumer-opensearch project
3. Now you can run OpenSearchConsumer