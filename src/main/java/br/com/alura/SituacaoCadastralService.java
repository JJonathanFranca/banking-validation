package br.com.alura;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SituacaoCadastralService {

    private final SituacaoCadastralRepository situacaoCadastralRepository;
    private final InativarAgenciaProducer inativarAgenciaProducer;

    SituacaoCadastralService(SituacaoCadastralRepository situacaoCadastralRepository, InativarAgenciaProducer inativarAgenciaProducer) {
        this.situacaoCadastralRepository = situacaoCadastralRepository;
        this.inativarAgenciaProducer = inativarAgenciaProducer;
    }

    public void alterar(Agencia agencia) {
        situacaoCadastralRepository.update("situacaoCadastral = ?1 where cnpj = ?2", agencia.getSituacaoCadastral(), agencia.getCnpj());
        if (agencia.getSituacaoCadastral().equals("INATIVO")) {
            inativarAgenciaProducer.enviarMensagem("remover-agencia", agencia);
        }
    }
}
