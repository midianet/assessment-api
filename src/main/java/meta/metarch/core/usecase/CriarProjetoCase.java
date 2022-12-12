package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.core.model.Projeto;
import meta.metarch.infra.database.ProjetoRepository;

@Service
@RequiredArgsConstructor
public class CriarProjetoCase {
    private final ProjetoRepository repository;

    @Transactional
    public Projeto execute(@NonNull final CriarProjetoCase.In in){
        return repository.save(Projeto.builder()
                .nome(in.nome)
                .build());
    }

    public record In (
        @NotBlank
        @Size(min = 3, max = 80)
        String nome){}
    
}