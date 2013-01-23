package br.unb.frank.ontology.frankmanagement.action;

import jade.content.AgentAction;

public class CreateWorkgroup implements AgentAction {

    private static final long serialVersionUID = 1L;

    private String alunoId;

    public String getAlunoId() {
	return alunoId;
    }

    public void setAlunoId(String alunoId) {
	this.alunoId = alunoId;
    }

}
