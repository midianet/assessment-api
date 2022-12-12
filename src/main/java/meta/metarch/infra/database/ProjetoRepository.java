package meta.metarch.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;

import meta.metarch.core.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto,Long> {

}
