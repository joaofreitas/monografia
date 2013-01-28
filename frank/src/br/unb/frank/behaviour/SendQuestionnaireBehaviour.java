package br.unb.frank.behaviour;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.action.SendQuestionnaire;

public class SendQuestionnaireBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    private MessageTemplate pattern;
    private ContentManager contentManager;
    private Codec codec = new SLCodec();
    private Ontology ontology = ModelInferOntology.getInstance();

    public SendQuestionnaireBehaviour(MessageTemplate pattern,
	    ContentManager contentManager) {
	this.pattern = pattern;
	this.contentManager = contentManager;
	contentManager.setValidationMode(false);
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);

	if (msg != null) {
	    try {
		ContentElement content = contentManager.extractContent(msg);
		Concept action = ((Action) content).getAction();

		if (action instanceof SendQuestionnaire) {

		    System.out.println("Vou enviar o questionario");
		    SendQuestionnaire contentElementQuestionnaire = (SendQuestionnaire) action;
		    ACLMessage questionMsg = new ACLMessage(ACLMessage.INFORM);
		    questionMsg.setLanguage(codec.getName());
		    questionMsg.setOntology(ontology.getName());
		    questionMsg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		    String destiny = contentElementQuestionnaire.getStudentId();
		    // TODO Deveria fazer lookup do workgroup do aluno no
		    // ambiente
		    AID alunoAID = new AID(AgentPrefixEnum.WORKGROUP + destiny,
			    AID.ISLOCALNAME);

		    contentManager.fillContent(
			    questionMsg,
			    new Action(alunoAID, contentElementQuestionnaire
				    .getQuestionnaire()));
		    questionMsg.addReceiver(alunoAID);
		    myAgent.send(questionMsg);

		}

	    } catch (UngroundedException e) {
		e.printStackTrace();
	    } catch (CodecException e) {
		e.printStackTrace();
	    } catch (OntologyException e) {
		e.printStackTrace();
	    }
	}

	block();
    }
}
