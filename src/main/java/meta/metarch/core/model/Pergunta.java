package meta.metarch.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pergunta {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 3, max = 400)
    @Column(nullable = false, length = 400)
    private String texto;

    @Size(max = 400)
    @Column(length = 400)
    private String ajuda;

    @NotNull
    @ManyToOne
    private Disciplina disciplina;

}
