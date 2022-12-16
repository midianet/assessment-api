package meta.metarch.infra.database.data;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import meta.metarch.core.model.Resposta;

public record RadarData(
    String grupo,
    Long pergunta,
    @Enumerated(EnumType.STRING)
    Resposta.Maturidade resposta){}