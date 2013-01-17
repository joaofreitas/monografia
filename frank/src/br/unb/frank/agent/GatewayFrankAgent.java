package br.unb.frank.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.GatewayAgent;

public class GatewayFrankAgent extends GatewayAgent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void processCommand(Object command) {
		System.out.println("Gateway chamado!!");

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("Ping");
		for (int i = 1; i <= 2; i++)
			msg.addReceiver(new AID("a" + i, AID.ISLOCALNAME));

		send(msg);

		releaseCommand(command);
	}

}
