package meta.metarch.core.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Disciplina;
import meta.metarch.infra.database.DisciplinaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObterDisciplinaCase {
    private final DisciplinaRepository repository;
    public Disciplina execute(@NonNull final Long id){
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Disciplina %s",id)));
    }

}
