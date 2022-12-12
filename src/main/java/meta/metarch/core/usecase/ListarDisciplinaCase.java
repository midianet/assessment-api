package meta.metarch.core.usecase;


import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Disciplina;
import meta.metarch.infra.database.DisciplinaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarDisciplinaCase {

    private final DisciplinaRepository repository;

    public Page<Disciplina> execute(@NonNull final String nome,
                                    @NonNull final Pageable page){
        return repository.findAll(
            Example.of(Disciplina.builder()
                .nome(nome)
                .build(),
            ExampleMatcher.matchingAll()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.startsWith())),
            page);
    }

}
