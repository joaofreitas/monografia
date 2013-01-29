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
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.concept.Answer;
import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;
import br.unb.frank.ontology.modelinfer.concept.LearningDimension;
import br.unb.frank.ontology.modelinfer.concept.Questionnaire;

public class InferenceBehaviour extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    MessageTemplate pattern;
    private ContentManager contentManager;
    private Codec codec = new SLCodec();
    private Ontology ontology = ModelInferOntology.getInstance();

    public InferenceBehaviour(MessageTemplate pattern,
	    ContentManager contentManager, Codec codec, Ontology ontology) {
	this.pattern = pattern;
	this.contentManager = contentManager;
	this.codec = codec;
	this.ontology = ontology;
    }

    @Override
    public void action() {
	ACLMessage msg = myAgent.receive(pattern);
	if (msg != null) {
	    ContentElement content;
	    try {
		content = contentManager.extractContent(msg);
		Concept concept = ((Action) content).getAction();

		if (isQuestionnaireConcept(concept)) {
		    Questionnaire questionnaire = (Questionnaire) concept;
		    CognitiveModel cognitiveModel = explicitInference(questionnaire);

		    System.out.println("Modelo Escolhido "
			    + cognitiveModel.getLearningStyle());

		    Result result = new Result((Action) content, cognitiveModel);
		    ACLMessage reply = msg.createReply();
		    reply.setPerformative(ACLMessage.INFORM);
		    getContentManager().fillContent(reply, result);
		    myAgent.send(reply);
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

    private CognitiveModel explicitInference(Questionnaire questionnaire) {

	CognitiveModel cognitiveModel = new CognitiveModel();
	Integer maxDimension = 0;
	Integer maxSum = 0;

	List dimensions = questionnaire.getLearningDimensions();
	Iterator it = dimensions.iterator();
	while (it.hasNext()) {
	    LearningDimension dimension = (LearningDimension) it.next();

	    List answers = dimension.getAnswers();
	    Integer sumOptions = getAnswerSum(answers);

	    if (sumOptions > maxSum) {
		maxSum = sumOptions;
		maxDimension = dimension.getDimension();
	    }
	}

	cognitiveModel.setLearningStyle(maxDimension);
	cognitiveModel.setPerformance(0.0f); // TODO Inferir Performance
	cognitiveModel.setQuestionnaire(questionnaire);

	return cognitiveModel;
    }

    private Integer getAnswerSum(List answers) {
	Integer sum = 0;
	Iterator itAnwser = answers.iterator();

	while (itAnwser.hasNext()) {
	    Answer answer = (Answer) itAnwser.next();
	    answer.getOption();
	    sum += answer.getOption();
	}

	return sum;
    }

    private boolean isQuestionnaireConcept(Concept concept) {
	return concept instanceof Questionnaire;
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

}
