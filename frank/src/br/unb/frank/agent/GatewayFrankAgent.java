package br.unb.frank.agent;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import jade.wrapper.gateway.GatewayAgent;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.domain.model.AnswerListMessage;
import br.unb.frank.domain.model.CreateAgentMessage;
import br.unb.frank.domain.model.DestroyAgentMessage;
import br.unb.frank.ontology.frankmanagement.FrankManagementOntology;
import br.unb.frank.ontology.frankmanagement.action.CreateWorkgroup;
import br.unb.frank.ontology.frankmanagement.action.DestroyWorkgroup;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.action.SendQuestionnaire;
import br.unb.frank.ontology.modelinfer.concept.Answer;
import br.unb.frank.ontology.modelinfer.concept.LearningDimension;
import br.unb.frank.ontology.modelinfer.concept.Questionnaire;

public class GatewayFrankAgent extends GatewayAgent {

    private static final long serialVersionUID = 1L;

    private Codec codec = new SLCodec();
    private Ontology ontology = FrankManagementOntology.getInstance();
    private Ontology modelInferOntology = ModelInferOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(ontology);
	getContentManager().registerOntology(modelInferOntology);

	criarWorkgroupTeste();
	enviarMensagemTeste();

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

		// TODO Deveria procurar interface no ambiente
		AID interfaceAID = new AID(AgentPrefixEnum.MANAGER.toString(),
			AID.ISLOCALNAME);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, cw));
		msg.addReceiver(interfaceAID);
		send(msg);
		// addBehaviour(new WaitServerResponse(this));

	    } else if (command instanceof DestroyAgentMessage) {
		DestroyWorkgroup cw = new DestroyWorkgroup();
		cw.setAlunoId(((DestroyAgentMessage) command).getAlunoId()
			.toString());

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		// TODO Deveria procurar interface no ambiente
		AID interfaceAID = new AID("interface", AID.ISLOCALNAME);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, cw));
		msg.addReceiver(interfaceAID);
		send(msg);
		// addBehaviour(new WaitServerResponse(this));

	    } else if (command instanceof AnswerListMessage) {
		// TODO Deveria procurar interface no ambiente
		AID interfaceAID = new AID(AgentPrefixEnum.MANAGER.toString(),
			AID.ISLOCALNAME);

		SendQuestionnaire sendQuestionnaire = new SendQuestionnaire();
		String alunoId = ((AnswerListMessage) command).getAlunoId()
			.toString();
		sendQuestionnaire.setStudentId(alunoId);
		// TODO Deve colocar o questionário do aluno
		// sendQuestionnaire.setQuestionnaire((((AnswerListMessage)
		// command).getListAnswer()));

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(modelInferOntology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
		msg.addReceiver(interfaceAID);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, sendQuestionnaire));

		send(msg);
		// addBehaviour(new WaitServerResponse(this));

	    }

	} catch (CodecException e) {
	    e.printStackTrace();
	} catch (OntologyException e) {
	    e.printStackTrace();
	}

	releaseCommand(command);
    }

    private void criarWorkgroupTeste() {
	CreateWorkgroup cw = new CreateWorkgroup();
	cw.setAlunoId("4");

	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	msg.setLanguage(codec.getName());
	msg.setOntology(ontology.getName());
	msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

	// TODO Deveria procurar interface no ambiente
	AID interfaceAID = new AID(AgentPrefixEnum.MANAGER.toString(),
		AID.ISLOCALNAME);

	try {
	    getContentManager().fillContent(msg, new Action(interfaceAID, cw));
	} catch (CodecException e) {
	    e.printStackTrace();
	} catch (OntologyException e) {
	    e.printStackTrace();
	}
	msg.addReceiver(interfaceAID);
	send(msg);
	// addBehaviour(new WaitServerResponse(this));
    }

    private void enviarMensagemTeste() {
	AID interfaceAID = new AID(AgentPrefixEnum.MANAGER.toString(),
		AID.ISLOCALNAME);

	Answer answer = new Answer();
	answer.setOption(1);

	List answers = new ArrayList();
	answers.add(answer);

	LearningDimension learningDimension = new LearningDimension();
	learningDimension.setAnswers(answers);
	learningDimension.setDimension(1);

	List learningDimensions = new ArrayList();
	learningDimensions.add(learningDimension);

	Questionnaire questionnaire = new Questionnaire();
	questionnaire.setName("Questionário 1");
	questionnaire.setLearningDimensions(learningDimensions);

	SendQuestionnaire actionSendQuestionnaire = new SendQuestionnaire();
	String alunoId = "4";
	actionSendQuestionnaire.setStudentId(alunoId);
	actionSendQuestionnaire.setQuestionnaire(questionnaire);

	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	msg.setLanguage(codec.getName());
	msg.setOntology(modelInferOntology.getName());
	msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
	msg.addReceiver(interfaceAID);

	try {

	    getContentManager().fillContent(msg,
		    new Action(interfaceAID, actionSendQuestionnaire));
	} catch (CodecException | OntologyException e) {
	    e.printStackTrace();
	}

	send(msg);
	// addBehaviour(new WaitServerResponse(this));
    }

    // class WaitServerResponse extends ParallelBehaviour {
    // private static final long serialVersionUID = 1L;
    //
    // WaitServerResponse(Agent a) {
    //
    // super(a, ParallelBehaviour.WHEN_ALL);
    //
    // addSubBehaviour(new WakerBehaviour(myAgent, 5000) {
    //
    // private static final long serialVersionUID = 1L;
    //
    // protected void handleElapsedTimeout() {
    // System.out
    // .println("\n\tNo response from server. Please, try later!");
    // }
    // });
    // }
    // }
}
