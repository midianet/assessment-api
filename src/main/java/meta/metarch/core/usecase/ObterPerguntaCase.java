package meta.metarch.core.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.core.model.Pergunta;
import meta.metarch.infra.database.PerguntaRepository;

@Service
@RequiredArgsConstructor
public class ObterPerguntaCase {
    private final PerguntaRepository repository;
    public Pergunta execute(@NonNull final Long id){
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Pergunta %s",id)));
    }

}