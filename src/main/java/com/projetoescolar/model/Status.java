package com.projetoescolar.model;

public enum Status {
	
	CURSANDO("Cursando"),
	TRANCADO("Trancado"),
	CANCELADO("Cancelado"),
	CONCLUIDO("Concluido");
	
	private String nome;
	private String valor;
	
	private Status(String nome) {
		
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor = this.name();
	}
	

	

}
