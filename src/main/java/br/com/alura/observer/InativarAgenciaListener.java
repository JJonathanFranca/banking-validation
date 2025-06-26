package br.com.alura.observer;

import br.com.alura.domain.Agencia;
import br.com.alura.messagerie.InativarAgenciaProducer;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InativarAgenciaListener {
    
    private final InativarAgenciaProducer inativarAgenciaProducer;

    InativarAgenciaListener(InativarAgenciaProducer inativarAgenciaProducer) {
        this.inativarAgenciaProducer = inativarAgenciaProducer;
    }

    @ConsumeEvent(value = "remover-agencia", blocking = true)
    public Uni<Void> processarEvento(Agencia agencia) {
        return inativarAgenciaProducer.enviaMensagem(agencia);

    }
}
