package com.projetoescolar.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Entity
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;
	
	private Double nota1;
	
	
	private Double nota2;
	
	
	private Double total;
	
	
	private Double media;
	
	@ManyToOne
	@ForeignKey(name = "aluno_id")
	private Aluno aluno;
	
	@Enumerated(value = EnumType.STRING)
	private StatusDisciplinaAluno  statusDisciplinaAluno;
	
	private Double notaTrabalho;
	
	private Double notaExercicioSala;
	
	public Double getNotaExercicioSala() {
		return notaExercicioSala;
	}
	
	public void setNotaExercicioSala(Double notaExercicioSala) {
		this.notaExercicioSala = notaExercicioSala;
	}
	
	public void setNotaTrabalho(Double notaTrabalho) {
		this.notaTrabalho = notaTrabalho;
	}
	
	public Double getNotaTrabalho() {
		return notaTrabalho;
	}
	
	
	public void setMedia(Double media) {
		this.media = media;
	}
	
	public Double getMedia() {
		return media;
	}
	
	public void setStatusDisciplinaAluno(StatusDisciplinaAluno statusDisciplinaAluno) {
		this.statusDisciplinaAluno = statusDisciplinaAluno;
	}
	
	public StatusDisciplinaAluno getStatusDisciplinaAluno() {
		return statusDisciplinaAluno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getNota1() {
		return nota1;
	}

	public void setNota1(Double nota1) {
		this.nota1 = nota1;
	}

	public Double getNota2() {
		return nota2;
	}

	public void setNota2(Double nota2) {
		this.nota2 = nota2;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
}
