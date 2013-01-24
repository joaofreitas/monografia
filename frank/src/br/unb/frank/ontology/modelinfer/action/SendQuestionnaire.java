package br.unb.frank.ontology.modelinfer.action;

import jade.content.AgentAction;
import jade.core.AID;
import br.unb.frank.ontology.modelinfer.concept.Questionnaire;

public class SendQuestionnaire implements AgentAction {

    private static final long serialVersionUID = 1L;
    private AID destiny;
    private Questionnaire questionnaire;

    public AID getDestiny() {
	return destiny;
    }

    public void setDestiny(AID destiny) {
	this.destiny = destiny;
    }

    public Questionnaire getQuestionnaire() {
	return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
	this.questionnaire = questionnaire;
    }

}
