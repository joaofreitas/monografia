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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Dimensao generated by hbm2java
 */
@Entity
@Table(name = "dimensao")
public class Dimensao implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private Set<Pergunta> perguntas = new HashSet<Pergunta>(0);

    public Dimensao() {
    }

    public Dimensao(int id, String nome) {
	this.id = id;
	this.nome = nome;
    }

    public Dimensao(int id, String nome, String descricao,
	    Set<Pergunta> perguntas) {
	this.id = id;
	this.nome = nome;
	this.descricao = descricao;
	this.perguntas = perguntas;
    }

    @Id
    @SequenceGenerator(name = "dimensao_id_seq", sequenceName = "dimensao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dimensao_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Column(name = "nome", nullable = false)
    @NotNull
    public String getNome() {
	return this.nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    @Column(name = "descricao")
    public String getDescricao() {
	return this.descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dimensao")
    public Set<Pergunta> getPerguntas() {
	return this.perguntas;
    }

    public void setPerguntas(Set<Pergunta> perguntas) {
	this.perguntas = perguntas;
    }

}
