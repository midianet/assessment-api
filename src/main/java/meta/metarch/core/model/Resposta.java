package meta.metarch.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime atualizacao;

    @NotNull
    @ManyToOne
    private Projeto projeto;

    @NotNull
    @ManyToOne
    private Pergunta pergunta;

    @NotNull
    @Column(nullable = false)
    private Maturidade maturidade;

    @AllArgsConstructor
    public enum Maturidade {
        ARRASTA(1), ENGATINHA(2), ANDA(3), CORRE(4), VOA(5);
        @Getter
        private Integer valor;
    }

}
