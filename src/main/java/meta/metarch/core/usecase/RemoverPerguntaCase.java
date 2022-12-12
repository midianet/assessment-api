package meta.metarch.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import meta.metarch.infra.database.PerguntaRepository;

@Service
@RequiredArgsConstructor
public class RemoverPerguntaCase {
    private final PerguntaRepository repository;
    private final ObterPerguntaCase obter;

    @Transactional
    public void execute(@NonNull final Long id){
        repository.delete(obter.execute(id));
    }

}