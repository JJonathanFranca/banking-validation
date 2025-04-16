package br.com.alura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;

@ApplicationScoped
public class SituacaoCadastralService {

    private final SituacaoCadastralRepository situacaoCadastralRepository;
    private final Event<Agencia> event;

    SituacaoCadastralService(SituacaoCadastralRepository situacaoCadastralRepository, InativarAgenciaProducer inativarAgenciaProducer, Event<Agencia> event) {
        this.situacaoCadastralRepository = situacaoCadastralRepository;
        this.event = event;
    }

    public void alterar(Agencia agencia) {
        situacaoCadastralRepository.update("situacaoCadastral = ?1 where cnpj = ?2", agencia.getSituacaoCadastral(), agencia.getCnpj());
        event.fire(agencia);
    }
}
