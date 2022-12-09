package meta.metarch.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Disciplina {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

}
