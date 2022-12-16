package meta.metarch.core.usecase;


import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Assessment;
import meta.metarch.infra.database.data.AssessmentData;
import meta.metarch.infra.database.RespostaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MontarAssessmentCase {
    private final RespostaRepository repository;
    private final ObterDisciplinaCase obterDisciplina;
    private final ObterProjetoCase obterProjeto;

    public Assessment execute(@NonNull  final Long projetoId,
                                    @Nullable final Long disciplinaId){
        obterProjeto.execute(projetoId);
        List<AssessmentData> data;
        if(Objects.nonNull(disciplinaId)){
            obterDisciplina.execute(disciplinaId);
            data = repository.montarAssessmentData(projetoId,disciplinaId);
        }else{
            data = repository.montarAssessmentData(projetoId);
        }
        return Assessment.builder()
            .grupos(data.stream()
                .collect(Collectors.groupingBy(AssessmentData::disciplinaId))
                .entrySet().stream()
                    .map(grupo -> new Assessment.Grupo(
                        grupo.getKey(),
                        grupo.getValue().stream()
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Grupo Sem elementos")).disciplinaNome(),
                        grupo.getValue().stream()
                            .map(resposta -> new Assessment.Elemento(
                                resposta.respostaId(),
                                resposta.perguntaId(),
                                resposta.perguntaTexto(),
                                resposta.perguntaAjuda(),
                                resposta.maturidade()))
                    .toList()))
                .toList())
            .build();
    }

}