package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.core.model.Disciplina;
import meta.metarch.infra.database.DisciplinaRepository;

@Service
@RequiredArgsConstructor
public class CriarDisciplinaCase {
    private final DisciplinaRepository repository;

    @Transactional
    public Disciplina execute(@NonNull final In in){
        return repository.save(Disciplina.builder()
            .nome(in.nome)
            .build());
    }

    public record In (
            @NotBlank
            @Size(min = 3, max = 80)
            String nome

    ){}

    //public record Out(Long id,String nome){}
}
