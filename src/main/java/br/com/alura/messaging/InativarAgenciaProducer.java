package br.com.alura.messaging;

import br.com.alura.domain.Agencia;
import br.com.alura.messaging.configuration.KafkaConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;

@ApplicationScoped
public class InativarAgenciaProducer {

    private final MutinyEmitter<String> emitter;
    private final ObjectMapper objectMapper;
    private final KafkaConfiguration kafkaConfiguration;

    public InativarAgenciaProducer(@Channel("remover-agencia-channel") MutinyEmitter<String> emitter, KafkaConfiguration kafkaConfiguration) {
        this.emitter = emitter;
        this.objectMapper = new ObjectMapper();
        this.kafkaConfiguration = kafkaConfiguration;
    }

    public Uni<Void> enviarMensagemKafkaConfiguration(Agencia agencia) {
        try {
            String agenciaConvertida = objectMapper.writeValueAsString(agencia);
            return kafkaConfiguration.enviarMensagem("remover-agencia", "", agenciaConvertida);
        } catch (JsonProcessingException e) {
            return Uni.createFrom().failure(e);
        }
    }

    public Uni<Void> enviarMensagemSmallRyeMutinyEmiter(Agencia agencia) {
        try {
            String agenciaConvertida = objectMapper.writeValueAsString(agencia);
            return emitter.send(agenciaConvertida);
        } catch (JsonProcessingException e) {
            return Uni.createFrom().failure(e);
        }
    }
}
