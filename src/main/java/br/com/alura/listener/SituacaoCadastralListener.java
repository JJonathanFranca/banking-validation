package br.com.alura.listener;

import br.com.alura.domain.Agencia;
import br.com.alura.messaging.InativarAgenciaProducer;
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
        if (agencia.getSituacaoCadastral().equals("INATIVO")) {
            inativarAgenciaProducer.enviarMensagem(agencia);
        }
    }
}
