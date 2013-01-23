package br.unb.frank.behaviour;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import br.unb.frank.agent.workgroup.WorkGroupAgent;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.ontology.frankmanagement.action.CreateWorkgroup;
import br.unb.frank.ontology.frankmanagement.action.DestroyWorkgroup;

public class ManageWorkgroupBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    private MessageTemplate pattern;
    private AgentContainer container;
    private ContentManager contentManager;

    public ManageWorkgroupBehaviour(MessageTemplate pattern,
	    AgentContainer container, ContentManager contentManager) {
	this.pattern = pattern;
	this.container = container;
	this.contentManager = contentManager;
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);

	if (msg != null) {
	    ContentElement content;
	    try {
		content = contentManager.extractContent(msg);
		Concept ca = ((Action) content).getAction();

		System.out.println("Create received" + ca.getClass().getName());
		if (ca instanceof CreateWorkgroup) {
		    CreateWorkgroup cw = (CreateWorkgroup) ca;
		    System.out.println("Vou iniciar " + cw.getAlunoId());
		} else if (ca instanceof DestroyWorkgroup) {
		    DestroyWorkgroup cw = (DestroyWorkgroup) ca;
		    System.out.println("Vou encerrar " + cw.getAlunoId());
		}
	    } catch (CodecException | OntologyException e) {
		e.printStackTrace();
	    }
	}

	block();
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
