package br.unb.frank.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.GatewayAgent;

import java.io.IOException;

import br.unb.frank.domain.model.CreateAgentMessage;
import br.unb.frank.domain.model.DestroyAgentMessage;

public class GatewayFrankAgent extends GatewayAgent {

    private static final long serialVersionUID = 1L;

    @Override
    protected void processCommand(Object command) {

	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
	msg.addReceiver(new AID("interface", AID.ISLOCALNAME));

	try {
	    if (command instanceof CreateAgentMessage) {
		msg.setContentObject((CreateAgentMessage) command);
	    } else if (command instanceof DestroyAgentMessage) {
		msg.setContentObject((DestroyAgentMessage) command);
	    }
	    send(msg);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	releaseCommand(command);
    }
}
