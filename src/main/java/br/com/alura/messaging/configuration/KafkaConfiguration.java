package br.com.alura.messaging.configuration;

import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import jakarta.enterprise.context.ApplicationScoped;
import io.vertx.kafka.client.producer.KafkaProducer;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class KafkaConfiguration {

    private KafkaProducer<String, String> producer;
    private final Vertx vertx;

    public KafkaConfiguration(Vertx vertx) {
        this.vertx = vertx;

        Map<String, String> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = KafkaProducer.create(vertx, props);
    }

    public Uni<Void> enviarMensagem(String topic, String key, String value) {
        KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(topic, key, value);

        return Uni.createFrom().emitter(emmiter -> {
            producer.send(record, metadata -> {
                if (metadata.succeeded()) {
                    emmiter.complete(null);
                } else {
                    emmiter.fail(metadata.cause());
                }
            });
        });
    }
}
