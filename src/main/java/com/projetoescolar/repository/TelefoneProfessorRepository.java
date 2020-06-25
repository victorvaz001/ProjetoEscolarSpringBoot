package com.projetoescolar.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projetoescolar.model.TelefoneProfessor;


@Repository
@Transactional
public interface TelefoneProfessorRepository extends CrudRepository<TelefoneProfessor, Long> {
	
	
	@Query("select t from TelefoneProfessor t where t.professor.id = ?1")
	public List<TelefoneProfessor> getTelefoneProf(Long professorid);

}
