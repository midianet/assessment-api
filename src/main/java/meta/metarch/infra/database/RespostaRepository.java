package meta.metarch.infra.database;

import meta.metarch.core.model.Resposta;
import meta.metarch.infra.database.data.AssessmentData;
import meta.metarch.infra.database.data.RadarData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta,Long> {

    @Query("SELECT new meta.metarch.infra.database.data.AssessmentData(d.id, d.nome, p.id, p.texto, p.ajuda, r.id, r.maturidade ) FROM Pergunta p LEFT JOIN Resposta r ON r.pergunta.id = p.id AND r.projeto.id = :projetoId INNER JOIN Disciplina d ON p.disciplina.id = d.id ORDER BY d.nome, p.texto")
    List<AssessmentData> montarAssessmentData(Long projetoId);

    @Query("SELECT new meta.metarch.infra.database.data.AssessmentData(d.id, d.nome, p.id, p.texto, p.ajuda, r.id, r.maturidade ) FROM Pergunta p LEFT JOIN Resposta r ON r.pergunta.id = p.id AND r.projeto.id = :projetoId INNER JOIN Disciplina d ON p.disciplina.id = d.id AND d.id= :disciplinaId ORDER BY d.nome, p.texto")
    List<AssessmentData> montarAssessmentData(Long projetoId, Long disciplinaId);

    @Query("SELECT new meta.metarch.infra.database.data.RadarData(d.nome, p.id, r.maturidade) FROM Pergunta p INNER JOIN Disciplina d ON p.disciplina.id = d.id LEFT JOIN Resposta r ON p.id = r.pergunta.id AND r.projeto.id = :projetoId")
    List<RadarData> montarRadar(Long projetoId);

    /*
    select  count(p.id) perguntas,
           sum(r.maturidade) respondidas,
          d.id
  from pergunta p
left join resposta r
 on r.pergunta_id = p.id
and r.projeto_id=2
left join disciplina d
   on d.id = p.disciplina_id
group by d.id

     */


}
