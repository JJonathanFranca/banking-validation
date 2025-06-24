package br.com.alura.messaging.configuration;

import io.quarkus.kafka.client.runtime.KafkaRecorder;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.Config;

import java.util.Properties;

@ApplicationScoped
public class KafkaConfiguration {

    private final KafkaProducer<String, String> producer;

    public KafkaConfiguration(Config config) {
        String kafkaHost = config.getValue("kafka.producer.host", String.class);

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        this.producer = new KafkaProducer<>(properties);
    }

    public void enviarMensagem(String topico, String mensagem) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topico, mensagem);

        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                Log.info("A mensagem foi registrada no offset: " + metadata.offset());
            } else {
                exception.getStackTrace();
            }
        });
    }
}
