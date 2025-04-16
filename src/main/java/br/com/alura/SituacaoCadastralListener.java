package br.com.alura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;

@ApplicationScoped
public class SituacaoCadastralListener {

    private final InativarAgenciaProducer inativarAgenciaProducer;

    public SituacaoCadastralListener(InativarAgenciaProducer inativarAgenciaProducer) {
        this.inativarAgenciaProducer = inativarAgenciaProducer;
    }

    public void processarEvento(@Observes(during = TransactionPhase.AFTER_SUCCESS) Agencia agencia) {
        if ("INATIVO".equals(agencia.getSituacaoCadastral())) {
            inativarAgenciaProducer.enviarMensagem("remover-agencia", agencia);
        }
    }
}
