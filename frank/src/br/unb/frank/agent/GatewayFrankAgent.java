package br.unb.frank.agent;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.GatewayAgent;
import br.unb.frank.domain.model.CreateAgentMessage;
import br.unb.frank.domain.model.DestroyAgentMessage;
import br.unb.frank.ontology.frankmanagement.FrankManagementOntology;
import br.unb.frank.ontology.frankmanagement.action.CreateWorkgroup;
import br.unb.frank.ontology.frankmanagement.action.DestroyWorkgroup;

public class GatewayFrankAgent extends GatewayAgent {

    private static final long serialVersionUID = 1L;

    private Codec codec = new SLCodec();
    private Ontology ontology = FrankManagementOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(ontology);

	super.setup();
    }

    @Override
    protected void processCommand(Object command) {

	try {
	    if (command instanceof CreateAgentMessage) {
		CreateWorkgroup cw = new CreateWorkgroup();
		cw.setAlunoId(((CreateAgentMessage) command).getAlunoId()
			.toString());

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		AID interfaceAID = new AID("interface", AID.ISLOCALNAME);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, cw));
		msg.addReceiver(interfaceAID);
		send(msg);
		addBehaviour(new WaitServerResponse(this));

	    } else if (command instanceof DestroyAgentMessage) {
		DestroyWorkgroup cw = new DestroyWorkgroup();
		cw.setAlunoId(((DestroyAgentMessage) command).getAlunoId()
			.toString());

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		AID interfaceAID = new AID("interface", AID.ISLOCALNAME);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, cw));
		msg.addReceiver(interfaceAID);
		send(msg);
		addBehaviour(new WaitServerResponse(this));

	    }

	} catch (CodecException e) {
	    e.printStackTrace();
	} catch (OntologyException e) {
	    e.printStackTrace();
	}

	releaseCommand(command);
    }

    class WaitServerResponse extends ParallelBehaviour {
	private static final long serialVersionUID = 1L;

	WaitServerResponse(Agent a) {

	    super(a, ParallelBehaviour.WHEN_ALL);

	    addSubBehaviour(new WakerBehaviour(myAgent, 5000) {

		private static final long serialVersionUID = 1L;

		protected void handleElapsedTimeout() {
		    System.out
			    .println("\n\tNo response from server. Please, try later!");
		}
	    });
	}
    }
}
