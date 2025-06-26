package br.com.alura.service.events;

import br.com.alura.domain.Agencia;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventBusProducer {
    
    private final EventBus eventBus;

    EventBusProducer(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public Uni<Void> publicaRemocaoAgencia(Agencia agencia) {
        return eventBus.request("remover-agencia", agencia)
                .onItem()
                    .ignore().andContinueWithNull();
    }
}
