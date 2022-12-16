package meta.metarch.core.usecase;

import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Assessment;
import meta.metarch.infra.database.RespostaRepository;
import meta.metarch.infra.database.data.AssessmentData;
import meta.metarch.infra.database.data.RadarData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MontarRadarCase {
    private final RespostaRepository repository;
    private final ObterDisciplinaCase obterDisciplina;
    private final ObterProjetoCase obterProjeto;

    public List<Assessment.Radar> execute(@NonNull final Long projetoId){
        obterProjeto.execute(projetoId);
        return repository.montarRadar(projetoId).stream()
            .collect(Collectors.groupingBy(RadarData::grupo))
            .entrySet().stream()
                .map(grupo -> new Assessment.Radar(
                    grupo.getKey(),
                    (double) (grupo.getValue().stream().count() * (grupo.getValue().stream().count() * 5))))
                .toList();

//        return Assessment.builder()
//            .grupos(data.stream()
//                .collect(Collectors.groupingBy(AssessmentData::disciplinaId))
//                .entrySet().stream()
//                    .map(grupo -> new Assessment.Grupo(
//                        grupo.getKey(),
//                        grupo.getValue().stream()
//                            .findFirst()
//                            .orElseThrow(() -> new RuntimeException("Grupo Sem elementos")).disciplinaNome(),
//                        grupo.getValue().stream()
//                            .map(resposta -> new Assessment.Elemento(
//                                resposta.respostaId(),
//                                resposta.perguntaId(),
//                                resposta.perguntaTexto(),
//                                resposta.perguntaAjuda(),
//                                resposta.maturidade()))
//                    .toList()))
//                .toList())
//            .build();
    }

}