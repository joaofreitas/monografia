package br.unb.frank.agent;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.behaviour.ReceiveManipulateAgentBehaviour;

public class InterfaceAgent extends Agent {

    private static final long serialVersionUID = 2714298832678014462L;

    MessageTemplate pattern;

    @Override
    protected void setup() {
	pattern = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

	// Registering the SL content
	getContentManager().registerLanguage(new SLCodec(),
		FIPANames.ContentLanguage.FIPA_SL);

	// Registering ontology of Jade Management
	getContentManager().registerOntology(
		JADEManagementOntology.getInstance());

	addBehaviour(new ReceiveManipulateAgentBehaviour(pattern,
		getContainerController()));
	super.setup();
    }

}
