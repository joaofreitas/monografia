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
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.action.SendQuestionnaire;
import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;
import br.unb.frank.ontology.modelinfer.concept.Questionnaire;
import br.unb.frank.ontology.modelinfer.predicate.Owns;

public class WorkgroupBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    private MessageTemplate pattern;
    private ContentManager contentManager;
    private Codec codec;
    private Ontology ontology;

    private AID affectiveAID;
    private AID cognitiveAID;
    private AID metacognitiveAID;

    private Owns own;

    public WorkgroupBehaviour(MessageTemplate pattern,
	    ContentManager contentManager, AID affectiveAID, AID cognitiveAID,
	    AID metacognitiveAID, Owns own) {
	super();
	this.pattern = pattern;
	this.contentManager = contentManager;
	this.affectiveAID = affectiveAID;
	this.cognitiveAID = cognitiveAID;
	this.metacognitiveAID = metacognitiveAID;

	this.own = own;
	codec = new SLCodec();
	ontology = ModelInferOntology.getInstance();
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);
	if (msg != null) {

	    try {
		ContentElement content = getContentManager()
			.extractContent(msg);

		if (isAbstractAction(content)) {
		    Concept action = ((Action) content).getAction();

		    if (isSendQuestionnaireAction(action)) {
			SendQuestionnaire sendQuestionnaireAction = (SendQuestionnaire) action;
			// Enviando somente o questionário
			Questionnaire questionnaire = sendQuestionnaireAction
				.getQuestionnaire();

			ACLMessage questionMsg = new ACLMessage(
				ACLMessage.REQUEST);
			questionMsg.setLanguage(codec.getName());
			questionMsg.setOntology(ontology.getName());
			questionMsg
				.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
			questionMsg.addReceiver(getCognitiveAID());

			getContentManager().fillContent(questionMsg,
				new Action(getCognitiveAID(), questionnaire));

			myAgent.send(questionMsg);
		    }

		} else if (isResultMessage(content)) {
		    Result result = (Result) content;
		    CognitiveModel cognitiveModel = (CognitiveModel) result
			    .getValue();

		    own.setCognitiveModel(cognitiveModel);
		    System.out.println("Resposta Recebida");
		    // TODO Deveria fazer lookup do manager do aluno no
		    // ambiente
		    AID managerAID = new AID(
			    AgentPrefixEnum.MANAGER.toString(), AID.ISLOCALNAME);

		    ACLMessage successMsg = new ACLMessage(ACLMessage.INFORM);
		    successMsg.setOntology(ontology.getName());
		    successMsg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
		    successMsg.addReceiver(managerAID);

		    myAgent.send(successMsg);
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

    private boolean isSendQuestionnaireAction(Concept action) {
	return action instanceof SendQuestionnaire;
    }

    private boolean isAbstractAction(ContentElement contentElement) {
	return contentElement instanceof Action;
    }

    private boolean isResultMessage(ContentElement contentElement) {
	return contentElement instanceof Result;
    }

    public MessageTemplate getPattern() {
	return pattern;
    }

    public void setPattern(MessageTemplate pattern) {
	this.pattern = pattern;
    }

    public ContentManager getContentManager() {
	return contentManager;
    }

    public void setContentManager(ContentManager contentManager) {
	this.contentManager = contentManager;
    }

    public Codec getCodec() {
	return codec;
    }

    public void setCodec(Codec codec) {
	this.codec = codec;
    }

    public Ontology getOntology() {
	return ontology;
    }

    public void setOntology(Ontology ontology) {
	this.ontology = ontology;
    }

    public AID getAffectiveAID() {
	return affectiveAID;
    }

    public void setAffectiveAID(AID affectiveAID) {
	this.affectiveAID = affectiveAID;
    }

    public AID getCognitiveAID() {
	return cognitiveAID;
    }

    public void setCognitiveAID(AID cognitiveAID) {
	this.cognitiveAID = cognitiveAID;
    }

    public AID getMetacognitiveAID() {
	return metacognitiveAID;
    }

    public void setMetacognitiveAID(AID metacognitiveAID) {
	this.metacognitiveAID = metacognitiveAID;
    }
}
