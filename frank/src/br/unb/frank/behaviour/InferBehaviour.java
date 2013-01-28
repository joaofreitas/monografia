package br.unb.frank.behaviour;

import jade.content.ContentManager;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;

public class InferBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    private MessageTemplate pattern;
    private AgentContainer container;
    private ContentManager contentManager;

    public InferBehaviour(MessageTemplate pattern, AgentContainer container,
	    ContentManager contentManager) {
	this.pattern = pattern;
	this.container = container;
	this.contentManager = contentManager;
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);

	if (msg != null) {
	    System.out.println("Chegou!");
	}

	block();
    }

}
