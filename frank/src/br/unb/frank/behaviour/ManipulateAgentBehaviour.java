package br.unb.frank.behaviour;

import java.io.Serializable;

import br.unb.frank.agent.workgroup.WorkGroupAgent;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.domain.model.CreateAgentMessage;
import br.unb.frank.domain.model.DestroyAgentMessage;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.KillAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ManipulateAgentBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    private MessageTemplate pattern;

    private AgentContainer container;

    public ManipulateAgentBehaviour(MessageTemplate pattern,
	    AgentContainer container) {
	this.pattern = pattern;
	this.container = container;
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);

	if (msg != null) {
	    try {

		if (isDestroyMessage(msg)) {
		    DestroyAgentMessage content = (DestroyAgentMessage) msg
			    .getContentObject();
		    String alunoId = content.getAlunoId().toString();

		    stopWorkgroup(alunoId);
		} else {
		    CreateAgentMessage content = (CreateAgentMessage) msg
			    .getContentObject();
		    String alunoId = content.getAlunoId().toString();

		    if (!existsAgentWith(alunoId)) {
			createWorkgroup(alunoId);
		    }
		}
	    } catch (UnreadableException e) {
		e.printStackTrace();
	    }
	}

	block();
    }

    private boolean isDestroyMessage(ACLMessage msg) throws UnreadableException {

	Serializable content = msg.getContentObject();
	if (content instanceof DestroyAgentMessage) {
	    return true;
	}
	return false;

    }

    private boolean existsAgentWith(String alunoId) {

	try {
	    DFAgentDescription dfd = new DFAgentDescription();
	    AID aid = new AID(AgentPrefixEnum.WORKGROUP + alunoId,
		    AID.ISLOCALNAME);
	    dfd.setName(aid);
	    DFAgentDescription[] result = DFService.search(myAgent, dfd);

	    if (result != null && result.length > 1) {
		return true;
	    }
	} catch (FIPAException e) {
	    e.printStackTrace();
	}

	return false;
    }

    private void stopWorkgroup(String alunoId) {

	KillAgent ka = new KillAgent();
	ka.setAgent(new AID(AgentPrefixEnum.WORKGROUP + alunoId,
		AID.ISLOCALNAME));
	Action action = new Action(myAgent.getAMS(), ka);

	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	msg.setLanguage(new SLCodec().getName());
	msg.setOntology(JADEManagementOntology.NAME);
	msg.addReceiver(myAgent.getAMS());

	try {
	    myAgent.getContentManager().fillContent(msg, action);
	} catch (CodecException e) {
	    e.printStackTrace();
	} catch (OntologyException e) {
	    e.printStackTrace();
	}

	myAgent.send(msg);
    }

    private void createWorkgroup(String alunoId) {
	Object[] args = new Object[1];
	args[0] = alunoId;

	try {
	    AgentController wg = container.createNewAgent(
		    AgentPrefixEnum.WORKGROUP + alunoId,
		    WorkGroupAgent.class.getName(), args);
	    wg.start();
	} catch (StaleProxyException e) {
	    e.printStackTrace();
	}
    }

}
