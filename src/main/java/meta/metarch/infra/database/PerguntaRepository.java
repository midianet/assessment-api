package meta.metarch.infra.database;

import meta.metarch.core.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta,Long> {

}
