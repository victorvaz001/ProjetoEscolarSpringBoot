package com.projetoescolar.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projetoescolar.model.Disciplina;
import com.projetoescolar.model.Telefone;

@Repository
@Transactional
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {
	
	@Query("select d from Disciplina d where d.aluno.id = ?1")
	public List<Disciplina> getDisiciplina(Long alunoid);
	


}
