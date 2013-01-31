package br.unb.frank.entity;

// Generated 29/01/2013 21:58:44 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Resposta generated by hbm2java
 */
@Entity
@Table(name = "resposta")
public class Resposta implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Pergunta pergunta;
    private Questionario questionario;
    private Integer opcaoEscolhida;

    public Resposta() {
    }

    public Resposta(int id, int opcaoEscolhida) {
	this.id = id;
	this.opcaoEscolhida = opcaoEscolhida;
    }

    public Resposta(int id, Pergunta pergunta, int opcaoEscolhida) {
	this.id = id;
	this.pergunta = pergunta;
	this.opcaoEscolhida = opcaoEscolhida;
    }

    @Id
    @SequenceGenerator(name = "resposta_id_seq", sequenceName = "resposta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resposta_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pergunta")
    public Pergunta getPergunta() {
	return this.pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
	this.pergunta = pergunta;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_questionario")
    public Questionario getQuestionario() {
	return this.questionario;
    }

    public void setQuestionario(Questionario questionario) {
	this.questionario = questionario;
    }

    @Column(name = "opcao_escolhida", nullable = false)
    public Integer getOpcaoEscolhida() {
	return this.opcaoEscolhida;
    }

    public void setOpcaoEscolhida(Integer opcaoEscolhida) {
	this.opcaoEscolhida = opcaoEscolhida;
    }

}