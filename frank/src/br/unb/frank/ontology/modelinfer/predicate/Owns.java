package br.unb.frank.ontology.modelinfer.predicate;

import jade.content.Predicate;
import jade.core.AID;
import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;

public class Owns implements Predicate {

    private static final long serialVersionUID = 1L;
    private AID student;
    private CognitiveModel cognitiveModel;

    public AID getStudent() {
	return student;
    }

    public void setStudent(AID student) {
	this.student = student;
    }

    public CognitiveModel getCognitiveModel() {
	return cognitiveModel;
    }

    public void setCognitiveModel(CognitiveModel cognitiveModel) {
	this.cognitiveModel = cognitiveModel;
    }

}
