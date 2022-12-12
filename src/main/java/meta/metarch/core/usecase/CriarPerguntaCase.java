package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.core.model.Pergunta;
import meta.metarch.infra.database.PerguntaRepository;

@Service
@RequiredArgsConstructor
public class CriarPerguntaCase {
    private final PerguntaRepository repository;
    private final ObterDisciplinaCase obterDisciplina;

    @Transactional
    public Pergunta execute(@NonNull final In in){
        return repository.save(Pergunta.builder()
            .texto(in.texto)
            .ajuda(in.ajuda)
            .disciplina(obterDisciplina.execute(in.disciplinaId))
            .build());
    }

    public record In (
        @NotBlank
        @Size(min = 3, max = 400)
        String texto,

        @Size(max = 400)
        String ajuda,

        @NonNull
        Long disciplinaId){}

}