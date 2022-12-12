package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.infra.database.DisciplinaRepository;

@Service
@RequiredArgsConstructor
public class RemoverDisciplinaCase {
    private final DisciplinaRepository repository;
    private final ObterDisciplinaCase obter;

    @Transactional
    public void execute(@NonNull final Long id){
        repository.delete(obter.execute(id));
    }

}
