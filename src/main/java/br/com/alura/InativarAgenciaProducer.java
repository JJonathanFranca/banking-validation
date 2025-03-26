package br.com.alura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InativarAgenciaProducer {

    private final KafkaConfiguration kafkaConfiguration;

    public InativarAgenciaProducer(KafkaConfiguration kafkaConfiguration) {
        this.kafkaConfiguration = kafkaConfiguration;
    }

    public void enviarMensagem(String topico, Agencia agencia) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String agenciaConvertida = objectMapper.writeValueAsString(agencia);
            kafkaConfiguration.enviarMensagem(topico, agenciaConvertida);
        } catch (JsonProcessingException e) {
            Log.error(e.getMessage());
            throw new RuntimeException();
        }
     }
}
