package meta.metarch.core.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.core.model.Projeto;
import meta.metarch.infra.database.ProjetoRepository;

@Service
@RequiredArgsConstructor
public class ObterProjetoCase {
    private final ProjetoRepository repository;
    public Projeto execute(@NonNull final Long id){
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Projeto %s",id)));
    }

}