package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Disciplina;
import meta.metarch.infra.database.DisciplinaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlterarDisciplinaCase {

    private final DisciplinaRepository repository;
    private final ObterDisciplinaCase obterDisciplina;

    @Transactional
    public void execute(@NonNull final Long id, @NonNull final In in){
        final var persistent = obterDisciplina.execute(id);
        BeanUtils.copyProperties(in,persistent,"id");
        repository.save(persistent);
    }

    public record In(String nome){}

}
