package br.unb.frank.domain.command;

import java.io.Serializable;

import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;

public class RequestCognitiveModelCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long alunoId;
    private CognitiveModel cognitiveModel;

    public Long getAlunoId() {
	return alunoId;
    }

    public void setAlunoId(Long alunoId) {
	this.alunoId = alunoId;
    }

    public CognitiveModel getCognitiveModel() {
	return cognitiveModel;
    }

    public void setCognitiveModel(CognitiveModel cognitiveModel) {
	this.cognitiveModel = cognitiveModel;
    }

}
