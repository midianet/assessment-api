package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.infra.database.ProjetoRepository;

@Service
@RequiredArgsConstructor
public class AlterarProjetoCase {
    private final ProjetoRepository repository;
    private final ObterProjetoCase obter;

    @Transactional
    public void execute(@NonNull final Long id, @NonNull final AlterarProjetoCase.In in){
        final var persistent = obter.execute(id);
        BeanUtils.copyProperties(in,persistent,"id");
        repository.save(persistent);
    }

    public record In(String nome){}

}
