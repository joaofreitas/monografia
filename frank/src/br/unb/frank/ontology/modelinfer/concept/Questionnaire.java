package br.unb.frank.ontology.modelinfer.concept;

import jade.content.Concept;
import jade.util.leap.List;

public class Questionnaire implements Concept {

    private static final long serialVersionUID = 1L;
    private String name;
    private List learningDimensions;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List getLearningDimensions() {
	return learningDimensions;
    }

    public void setLearningDimensions(List learningDimensions) {
	this.learningDimensions = learningDimensions;
    }

}
