package br.unb.frank.ontology.modelinfer.action;

import jade.content.AgentAction;
import br.unb.frank.ontology.modelinfer.concept.Questionnaire;

public class SendQuestionnaire implements AgentAction {

    private static final long serialVersionUID = 1L;
    private String studentId;
    private Questionnaire questionnaire;

    public String getStudentId() {
	return studentId;
    }

    public void setStudentId(String id) {
	this.studentId = id;
    }

    public Questionnaire getQuestionnaire() {
	return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
	this.questionnaire = questionnaire;
    }

}
