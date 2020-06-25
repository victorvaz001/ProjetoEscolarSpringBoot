package com.projetoescolar.model;

public enum StatusDisciplinaAluno {
	
	APROVADO("Aprovado"),
	REPROVADO("Reprovado"),
	RECUPERAÇÃO("Recuperacao"),
	SEGUNDA_CHAMADA("Segunda-Chamada"),
	EM_AVALIACAO("Em-Avaliacao");
	
	
	private String nome;
	private String valor;
	
	private StatusDisciplinaAluno(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
