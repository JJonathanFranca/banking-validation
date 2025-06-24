package br.com.alura.messaging;

import br.com.alura.domain.Agencia;
import br.com.alura.messaging.configuration.KafkaConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InativarAgenciaProducer {

    private KafkaConfiguration kafkaConfiguration;

    public InativarAgenciaProducer(KafkaConfiguration kafkaConfiguration) {
        this.kafkaConfiguration = kafkaConfiguration;
    }

    public void enviarMensagem(Agencia agencia) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String agenciaConvertida = objectMapper.writeValueAsString(agencia);
            kafkaConfiguration.enviarMensagem("remover-agencia", agenciaConvertida);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
