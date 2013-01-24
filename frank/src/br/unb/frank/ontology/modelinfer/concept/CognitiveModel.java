package br.unb.frank.ontology.modelinfer.concept;

import jade.content.Concept;

public class CognitiveModel implements Concept {

    private static final long serialVersionUID = 1L;
    private Float performance;
    private Integer learningStyle;

    public Float getPerformance() {
	return performance;
    }

    public void setPerformance(Float performance) {
	this.performance = performance;
    }

    public Integer getLearningStyle() {
	return learningStyle;
    }

    public void setLearningStyle(Integer learningStyle) {
	this.learningStyle = learningStyle;
    }

}
