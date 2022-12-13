package meta.metarch.core.usecase;


import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Assessment;
import meta.metarch.infra.database.RespostaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MontarAssessmentCase {
    private final RespostaRepository repository;
    private final ObterDisciplinaCase obterDisciplina;
    private final ObterProjetoCase obterProjeto;

    public Page<Assessment> execute(@NonNull  final Long projetoId,
                                    @Nullable final Long disciplinaId,
                                    @NonNull  final Pageable page){

        if(Objects.nonNull(disciplinaId)){
            obterDisciplina.execute(disciplinaId);
            return repository.montarAssessment(projetoId,disciplinaId,page);
        }else{
            obterProjeto.execute(projetoId);
            return repository.montarAssessment(projetoId,page);
        }
    }

}