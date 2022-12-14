package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.infra.database.ProjetoRepository;

@Service
@RequiredArgsConstructor
public class RemoverProjetoCase {
    private final ProjetoRepository repository;
    private final ObterProjetoCase obter;

    @Transactional
    public void execute(@NonNull final Long id){
        repository.delete(obter.execute(id));
    }

}