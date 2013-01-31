package br.unb.frank.entity;

// Generated 29/01/2013 21:58:44 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Pergunta generated by hbm2java
 */
@Entity
@Table(name = "pergunta")
public class Pergunta implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Dimensao dimensao;
    private String titulo;
    private int peso;
    private Set<Resposta> respostas = new HashSet<Resposta>(0);

    public Pergunta() {
    }

    public Pergunta(int id, Dimensao dimensao, String titulo, int peso) {
	this.id = id;
	this.dimensao = dimensao;
	this.titulo = titulo;
	this.peso = peso;
    }

    public Pergunta(int id, Dimensao dimensao, String titulo, int peso,
	    Set<Resposta> respostas) {
	this.id = id;
	this.dimensao = dimensao;
	this.titulo = titulo;
	this.peso = peso;
	this.respostas = respostas;
    }

    @Id
    @SequenceGenerator(name = "pergunta_id_seq", sequenceName = "pergunta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pergunta_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dimensao", nullable = false)
    @NotNull
    public Dimensao getDimensao() {
	return this.dimensao;
    }

    public void setDimensao(Dimensao dimensao) {
	this.dimensao = dimensao;
    }

    @Column(name = "titulo", nullable = false)
    @NotNull
    public String getTitulo() {
	return this.titulo;
    }

    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    @Column(name = "peso", nullable = false)
    public int getPeso() {
	return this.peso;
    }

    public void setPeso(int peso) {
	this.peso = peso;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pergunta")
    public Set<Resposta> getRespostas() {
	return this.respostas;
    }

    public void setRespostas(Set<Resposta> respostas) {
	this.respostas = respostas;
    }

}
