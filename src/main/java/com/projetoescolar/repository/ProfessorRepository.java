package com.projetoescolar.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoescolar.model.Professor;

@Repository
@Transactional
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
