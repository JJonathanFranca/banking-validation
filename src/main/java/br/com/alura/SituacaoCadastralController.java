package br.com.alura;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/situacao-cadastral")
public class SituacaoCadastralController {

    private final SituacaoCadastralRepository situacaoCadastralRepository;

    SituacaoCadastralController(SituacaoCadastralRepository situacaoCadastralRepository) {
        this.situacaoCadastralRepository = situacaoCadastralRepository;
    }

    @POST
    @WithTransaction
    @NonBlocking
    public Uni<Void> cadastrar(Agencia agencia) {
        return this.situacaoCadastralRepository.persist(agencia).replaceWithVoid();
    }

    @GET
    @WithSession
    public Uni<List<Agencia>> buscarTodos() {
        return this.situacaoCadastralRepository.findAll().list();
    }

    @GET
    @WithSession
    @Path("{cnpj}")
    public Uni<RestResponse<Agencia>> buscarPorCnpj(String cnpj) {
        Uni<Agencia> agencia = this.situacaoCadastralRepository.findByCnpj(cnpj);
        return agencia
                // .onItem().ifNull().continueWith(RestResponse.noContent()) to do -> if null, retornar no content
                .onItem().ifNotNull().transform(RestResponse::ok);
        }
}
