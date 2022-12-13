package meta.metarch.core.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.infra.database.ProjetoRepository;
import meta.metarch.core.model.Projeto;


@Service
@RequiredArgsConstructor
public class ListarProjetoCase {
    private final ProjetoRepository repository;

    public Page<Projeto> execute(@NonNull final String nome,
                                 @NonNull final Pageable page){
        return repository.findAll(
                Example.of(Projeto.builder()
                                .nome(nome)
                                .build(),
                        ExampleMatcher.matchingAll()
                                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.startsWith())),
                page);
    }

}