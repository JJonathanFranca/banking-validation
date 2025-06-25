package br.com.alura.messaging;

import br.com.alura.domain.Agencia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class InativarAgenciaProducer {

    private Emitter<String> emitter;

    private final ObjectMapper objectMapper;

    public InativarAgenciaProducer(@Channel("remover-agencia-channel") Emitter<String> emitter) {
        this.objectMapper = new ObjectMapper();
        this.emitter = emitter;
    }


}
