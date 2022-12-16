package meta.metarch.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Assessment{
    private List<Grupo> grupos;
    public record Grupo(
        Long id,
        String nome,
        List<Elemento> elementos){}

    public record Elemento(
       Long id,
       Long perguntaId,
       String perguntaTexto,
       String perguntaAjuda,
       Resposta.Maturidade maturidade
    ){}

    public record Radar(String grupo, Double valor){}

}
