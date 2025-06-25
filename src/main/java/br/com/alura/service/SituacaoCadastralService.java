package br.com.alura.service;

import br.com.alura.domain.Agencia;
import br.com.alura.messaging.InativarAgenciaProducer;
import br.com.alura.repository.SituacaoCadastralRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SituacaoCadastralService {

    private final SituacaoCadastralRepository situacaoCadastralRepository;
    private final InativarAgenciaProducer producer;

    public SituacaoCadastralService(SituacaoCadastralRepository situacaoCadastralRepository, InativarAgenciaProducer producer) {
        this.situacaoCadastralRepository = situacaoCadastralRepository;
        this.producer = producer;
    }

    @WithTransaction
    public Uni<Void> alterar(Agencia agencia) {

    }
}
