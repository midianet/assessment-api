package meta.metarch.core.usecase;

import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Assessment;
import meta.metarch.infra.database.RespostaRepository;
import meta.metarch.infra.database.data.RadarData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MontarRadarCase {
    private final RespostaRepository repository;
    private final ObterProjetoCase obterProjeto;

    public List<Assessment.Radar> execute(@NonNull final Long projetoId){
        obterProjeto.execute(projetoId);
        return repository.montarRadar(projetoId).stream()
            .collect(Collectors.groupingBy(RadarData::grupo))
            .entrySet().stream()
                .map(grupo -> new Assessment.Radar(
                    grupo.getKey(),
                        (((double)grupo.getValue().stream()
                        .collect(Collectors.summarizingLong(value -> Objects.nonNull(value.resposta()) ? value.resposta().getValor() : 0)).getSum()) / (5 * grupo.getValue().stream().count())*100)))
                        .toList();
    }

}