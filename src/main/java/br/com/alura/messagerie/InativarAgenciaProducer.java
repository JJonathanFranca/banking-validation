package br.com.alura.messagerie;

import br.com.alura.domain.Agencia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class InativarAgenciaProducer {

    private final Emitter<String> emitter;

    public InativarAgenciaProducer(@Channel("remover-agencia-channel") Emitter<String> emitter) {
        this.emitter = emitter;
    }

    
    public Uni<Void> enviaMensagem(Agencia agencia) {
        return Uni.createFrom().item(() -> {
            Log.info("Convertendo a agência para JSON: " + agencia.getCnpj());
            try {
                return new ObjectMapper().writeValueAsString(agencia);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Erro ao converter a agência para JSON", e);
            }
        })
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onItem()
            .transformToUni(json -> {
                Log.info("Enviando mensagem para o canal 'remover-agencia-channel': " + json);
                return Uni.createFrom().completionStage(() -> emitter.send(json));
            })
        .onFailure()
            .invoke(e -> Log.error("Erro ao enviar mensagem para o canal 'remover-agencia-channel': " + e.getMessage()))
        .replaceWithVoid();
    }
}
