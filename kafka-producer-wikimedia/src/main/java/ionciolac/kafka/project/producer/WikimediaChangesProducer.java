package ionciolac.kafka.project.producer;

public class WikimediaChangesProducer {

    public static void main(String[] args) {
        new ProducerService(new KafkaProducerService()).start();
    }
}