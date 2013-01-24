package br.unb.frank.ontology.modelinfer.concept;

import jade.content.Concept;

import java.util.List;

public class Questionnaire implements Concept {

    private static final long serialVersionUID = 1L;
    private String name;
    private List<Answer> answers;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Answer> getAnswers() {
	return answers;
    }

    public void setAnswers(List<Answer> answers) {
	this.answers = answers;
    }

}
