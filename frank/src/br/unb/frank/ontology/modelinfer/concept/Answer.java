package br.unb.frank.ontology.modelinfer.concept;

import jade.content.Concept;

public class Answer implements Concept {

    private static final long serialVersionUID = 1L;
    private Integer option;

    public Integer getOption() {
	return option;
    }

    public void setOption(Integer option) {
	this.option = option;
    }

}
