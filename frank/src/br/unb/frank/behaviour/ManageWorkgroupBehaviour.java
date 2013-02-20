package br.unb.frank.behaviour;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.KillAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import br.unb.frank.agent.workgroup.WorkgroupAgent;
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

		if (ca instanceof CreateWorkgroup) {
		    CreateWorkgroup cw = (CreateWorkgroup) ca;
		    String alunoId = cw.getAlunoId();
		    createWorkgroup(alunoId);
		} else if (ca instanceof DestroyWorkgroup) {
		    DestroyWorkgroup cw = (DestroyWorkgroup) ca;
		    String alunoId = cw.getAlunoId();
		    destroyWorkgroup(alunoId);
		}

	    } catch (CodecException | OntologyException e) {
		e.printStackTrace();
	    }
	}

	block();
    }

    private void createWorkgroup(String alunoId) {
	Object[] args = new Object[1];
	args[0] = alunoId;

	try {
	    AgentController wg = container.createNewAgent(
		    AgentPrefixEnum.WORKGROUP + alunoId,
		    WorkgroupAgent.class.getName(), args);
	    wg.start();
	} catch (StaleProxyException e) {
	    e.printStackTrace();
	}
    }

    private void destroyWorkgroup(String alunoId) {

	// TODO Deveria procurar interface no ambiente
	AID workgroupAID = new AID(AgentPrefixEnum.WORKGROUP + alunoId,
		AID.ISLOCALNAME);

	System.out.println("Terminando " + AgentPrefixEnum.WORKGROUP + alunoId);

	KillAgent ka = new KillAgent();
	ka.setAgent(workgroupAID);
	Action kaction = new Action();
	kaction.setActor(myAgent.getAMS());
	kaction.setAction(ka);

	ACLMessage AMSRequest = new ACLMessage(ACLMessage.REQUEST);
	AMSRequest.setSender(myAgent.getAID());
	AMSRequest.clearAllReceiver();
	AMSRequest.addReceiver(myAgent.getAMS());
	AMSRequest.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
	AMSRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

	AMSRequest
		.setOntology(jade.domain.JADEAgentManagement.JADEManagementOntology.NAME);
	try {
	    contentManager.fillContent(AMSRequest, kaction);
	} catch (CodecException e) {
	} catch (OntologyException e) {
	}

	myAgent.send(AMSRequest);
    }

}
