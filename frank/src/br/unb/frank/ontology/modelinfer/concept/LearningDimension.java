package br.unb.frank.ontology.modelinfer.concept;

import jade.content.Concept;
import jade.util.leap.List;

public class LearningDimension implements Concept {

    private static final long serialVersionUID = 1L;
    private Integer dimension;
    private List answers;

    public Integer getDimension() {
	return dimension;
    }

    public void setDimension(Integer dimension) {
	this.dimension = dimension;
    }

    public List getAnswers() {
	return answers;
    }

    public void setAnswers(List answers) {
	this.answers = answers;
    }

}
