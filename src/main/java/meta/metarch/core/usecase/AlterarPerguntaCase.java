package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.infra.database.PerguntaRepository;

@Service
@RequiredArgsConstructor
public class AlterarPerguntaCase {
    private final PerguntaRepository repository;
    private final ObterPerguntaCase obterPergunta;
    private final ObterDisciplinaCase obterDisciplina;

    @Transactional
    public void execute(@NonNull final Long id, @NonNull final In in) {
        final var persistent = obterPergunta.execute(id);
        BeanUtils.copyProperties(in, persistent, "id");
        if(persistent.getDisciplina().getId() != in.disciplinaId)
            persistent.setDisciplina(obterDisciplina.execute(in.disciplinaId));
        repository.save(persistent);
    }

    public record In(String nome, Long disciplinaId){}
}
