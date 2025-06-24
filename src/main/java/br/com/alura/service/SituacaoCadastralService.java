package br.com.alura.service;

import br.com.alura.domain.Agencia;
import br.com.alura.repository.SituacaoCadastralRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SituacaoCadastralService {

    private final SituacaoCadastralRepository situacaoCadastralRepository;
    private final Event<Agencia> event;

    public SituacaoCadastralService(SituacaoCadastralRepository situacaoCadastralRepository, Event<Agencia> event) {
        this.situacaoCadastralRepository = situacaoCadastralRepository;
        this.event = event;
    }

    @Transactional
    public void alterar(Agencia agencia) {
        situacaoCadastralRepository
                .update("situacaoCadastral = ?1 where cnpj = ?2", agencia.getSituacaoCadastral(), agencia.getCnpj());
        event.fire(agencia);
    }
}
