package meta.metarch.infra.database;

import meta.metarch.core.model.Assessment;
import meta.metarch.core.model.Resposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RespostaRepository extends JpaRepository<Resposta,Long> {

    @Query("SELECT new meta.metarch.core.model.Assessment(d.id, d.nome, p.id, p.texto, p.ajuda, r.id, r.projeto.id, r.maturidade ) FROM Pergunta p LEFT join Resposta r on r.pergunta.id = p.id and r.projeto.id = :projetoId INNER JOIN Disciplina d on p.disciplina.id = d.id order by d.nome, p.texto")
    Page<Assessment> montarAssessment(Long projetoId, Pageable page);

    @Query("SELECT new meta.metarch.core.model.Assessment(d.id, d.nome, p.id, p.texto, p.ajuda, r.id, r.projeto.id, r.maturidade ) FROM Pergunta p LEFT join Resposta r on r.pergunta.id = p.id and r.projeto.id = :projetoId INNER JOIN Disciplina d on p.disciplina.id = d.id and d.id= :disciplinaId order by d.nome, p.texto")
    Page<Assessment> montarAssessment(Long projetoId, Long disciplinaId, Pageable page);

}
