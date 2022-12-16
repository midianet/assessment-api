package meta.metarch.infra.database.data;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import meta.metarch.core.model.Resposta;

public record AssessmentData(
    Long disciplinaId,
    String disciplinaNome,
    Long perguntaId,
    String perguntaTexto,
    String perguntaAjuda,
    Long respostaId,
    @Enumerated(EnumType.STRING)
    Resposta.Maturidade maturidade){}