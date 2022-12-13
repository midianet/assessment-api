package meta.metarch.core.model;

public record Assessment(
  Long disciplinaId,
  String disciplinaNome,
  Long perguntaId,
  String perguntaTexto,
  String perguntaAjuda,
  Long respostaId,
  Long respostaProjetoId,
  Resposta.Maturidade RespostaMaturidade){}
