package br.com.alura.service;

import br.com.alura.domain.Agencia;
import br.com.alura.repository.SituacaoCadastralRepository;
import br.com.alura.service.events.EventBusProducer;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SituacaoCadastralService {

    private final SituacaoCadastralRepository situacaoCadastralRepository;
    private final EventBusProducer event;

    public SituacaoCadastralService(SituacaoCadastralRepository situacaoCadastralRepository, EventBusProducer event) {
        this.situacaoCadastralRepository = situacaoCadastralRepository;
        this.event = event;
    }

    @WithTransaction
    public Uni<Void> alterar(Agencia agencia) {
        return situacaoCadastralRepository
                .update("situacaoCadastral = ?1 where cnpj = ?2",
                        agencia.getSituacaoCadastral(), agencia.getCnpj())
                .onItem()
                    .transformToUni(t -> event.publicaRemocaoAgencia(agencia))
                .onItem()
                    .ignore().andContinueWithNull();
    }
}
