package meta.metarch.core.usecase;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Resposta;
import meta.metarch.infra.database.RespostaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SalvarRespostaCase {

    private final RespostaRepository repository;
    private final ObterProjetoCase obterProjeto;
    private final ObterPerguntaCase obterPergunta;
    public Resposta execute(@NonNull final Long projetoId, @NonNull final In resposta ){
        final var persistent = repository.findRespostaByProjetoIdAndPerguntaId(projetoId,resposta.perguntaId)
                .orElse(Resposta.builder()
                    .projeto(obterProjeto.execute(projetoId))
                    .pergunta(obterPergunta.execute(resposta.perguntaId))
                    .build());
         persistent.setAtualizacao(LocalDateTime.now());
         persistent.setMaturidade(resposta.maturidade);
         return repository.save(persistent);
    }
    public record In(Long perguntaId, Resposta.Maturidade maturidade){}

}
