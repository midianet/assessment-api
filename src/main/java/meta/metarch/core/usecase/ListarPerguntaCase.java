package meta.metarch.core.usecase;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Disciplina;
import meta.metarch.core.model.Pergunta;
import meta.metarch.infra.database.DisciplinaRepository;
import meta.metarch.infra.database.PerguntaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ListarPerguntaCase {
    private final PerguntaRepository repository;

    public Page<Pergunta> execute(@NonNull final String texto,
                                  @Nullable final Long disciplinaId,
                                  @NonNull final Pageable page){
        final var example = Pergunta.builder()
            .texto(texto);
        if(Objects.nonNull(disciplinaId))
            example.disciplina(Disciplina.builder()
                .id(disciplinaId)
                .build());
        return repository.findAll(
            Example.of(
                example.build(),
                ExampleMatcher.matchingAll()
                    .withMatcher("texto", ExampleMatcher.GenericPropertyMatchers
                        .contains()
                        .ignoreCase())),
                page);
    }

}