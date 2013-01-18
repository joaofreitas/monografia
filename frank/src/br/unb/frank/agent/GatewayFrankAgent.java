package br.unb.frank.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.GatewayAgent;
import br.unb.frank.domain.model.CreateAgentMessage;

public class GatewayFrankAgent extends GatewayAgent {

    private static final long serialVersionUID = 1L;

    @Override
    protected void processCommand(Object command) {

	if (command instanceof CreateAgentMessage) {
	    CreateAgentMessage createAgent = (CreateAgentMessage) command;
	    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
	    msg.setContent(createAgent.getAlunoId().toString());
	    msg.addReceiver(new AID("interface", AID.ISLOCALNAME));

	    send(msg);
	}

	releaseCommand(command);
    }

}
