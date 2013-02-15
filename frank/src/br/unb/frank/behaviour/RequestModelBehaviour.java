package br.unb.frank.behaviour;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;
import br.unb.frank.ontology.modelinfer.predicate.Owns;

public class RequestModelBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    private MessageTemplate pattern;
    private Codec codec;
    private Ontology ontology;

    private ContentManager contentManager;

    private Owns own;

    public RequestModelBehaviour(MessageTemplate pattern, Owns own,
	    ContentManager contentManager) {
	super();
	this.pattern = pattern;

	codec = new SLCodec();
	this.own = own;

	ontology = ModelInferOntology.getInstance();
	this.contentManager = contentManager;
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);

	if (msg != null) {
	    ACLMessage reply;
	    CognitiveModel cognitiveModel = own.getCognitiveModel();
	    if (cognitiveModel != null) {
		reply = new ACLMessage(ACLMessage.INFORM);
		reply.addReceiver(msg.getSender());
		reply.setLanguage(codec.getName());
		reply.setOntology(ontology.getName());
		reply.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		try {
		    Result result = new Result(new Action(myAgent.getAID(),
			    own.getCognitiveModel()), own.getCognitiveModel());
		    result.setValue(own.getCognitiveModel());

		    contentManager.fillContent(reply, result);
		    myAgent.send(reply);
		} catch (UngroundedException e) {
		    e.printStackTrace();
		} catch (CodecException e) {
		    e.printStackTrace();
		} catch (OntologyException e) {
		    e.printStackTrace();
		}
	    } else {
		reply = msg.createReply();
		reply.setPerformative(ACLMessage.FAILURE);
	    }

	    myAgent.send(reply);
	}
	block();
    }
}
