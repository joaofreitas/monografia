package br.unb.frank.ontology.modelinfer.predicate;

import jade.content.Predicate;
import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;
import br.unb.frank.ontology.modelinfer.concept.Student;

public class Owns implements Predicate {

    private static final long serialVersionUID = 1L;
    private Student student;
    private CognitiveModel cognitiveModel;

    public Student getStudent() {
	return student;
    }

    public void setStudent(Student student) {
	this.student = student;
    }

    public CognitiveModel getCognitiveModel() {
	return cognitiveModel;
    }

    public void setCognitiveModel(CognitiveModel cognitiveModel) {
	this.cognitiveModel = cognitiveModel;
    }

}
