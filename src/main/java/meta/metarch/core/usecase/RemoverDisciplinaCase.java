package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import meta.metarch.infra.database.DisciplinaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoverDisciplinaCase {
    private final DisciplinaRepository repository;
    private final ObterDisciplinaCase obterDisciplina;

    @Transactional
    public void execute(@NonNull final Long id){
        repository.delete(obterDisciplina.execute(id));
    }

}
