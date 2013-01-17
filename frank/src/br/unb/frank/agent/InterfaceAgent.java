package br.unb.frank.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import br.unb.frank.agent.workgroup.WorkGroupAgent;
import br.unb.frank.domain.AgentPrefixEnum;

public class InterfaceAgent extends Agent {

    private static final long serialVersionUID = 2714298832678014462L;

    MessageTemplate pattern;
    private AgentContainer container;

    @Override
    protected void setup() {
	pattern = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

	addBehaviour(new CyclicBehaviour() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void action() {
		ACLMessage msg = receive(pattern);
		if (msg != null) {
		    String alunoId = msg.getContent();
		    createWorkgroup(alunoId);

		}
		block();
	    }

	    private void createWorkgroup(String alunoId) {
		Object[] args = new Object[1];
		args[0] = alunoId;

		try {
		    AgentController wg = getContainer().createNewAgent(
			    AgentPrefixEnum.WORKGROUP + alunoId,
			    WorkGroupAgent.class.getName(), args);
		    wg.start();
		} catch (StaleProxyException e) {
		    e.printStackTrace();
		}
	    }
	});

	super.setup();
    }

    public AgentContainer getContainer() {
	if (container == null) {
	    container = getContainerController();
	}
	return container;
    }

}
