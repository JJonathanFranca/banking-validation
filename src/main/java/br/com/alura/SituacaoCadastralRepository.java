package br.com.alura;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SituacaoCadastralRepository implements PanacheRepository<Agencia> {

    public Uni<Agencia> findByCnpj(String cnpj) {
        return find("cnpj", cnpj).firstResult();
    }
}
