package br.unb.frank.agent;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import jade.wrapper.gateway.GatewayAgent;
import br.unb.frank.behaviour.ReceiveMessageBehaviour;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.domain.command.AnswerCommand;
import br.unb.frank.domain.command.CreateAgentCommand;
import br.unb.frank.domain.command.DestroyAgentCommand;
import br.unb.frank.domain.command.DimensionCommand;
import br.unb.frank.domain.command.ProcessQuestionnaireCommand;
import br.unb.frank.domain.command.RequestCognitiveModelCommand;
import br.unb.frank.ontology.frankmanagement.FrankManagementOntology;
import br.unb.frank.ontology.frankmanagement.action.CreateWorkgroup;
import br.unb.frank.ontology.frankmanagement.action.DestroyWorkgroup;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.action.SendQuestionnaire;
import br.unb.frank.ontology.modelinfer.concept.Answer;
import br.unb.frank.ontology.modelinfer.concept.CognitiveModel;
import br.unb.frank.ontology.modelinfer.concept.LearningDimension;
import br.unb.frank.ontology.modelinfer.concept.Questionnaire;

public class GatewayFrankAgent extends GatewayAgent {

    private static final long serialVersionUID = 1L;

    private Codec codec = new SLCodec();
    private Ontology frankOntology = FrankManagementOntology.getInstance();
    private Ontology modelInferOntology = ModelInferOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(frankOntology);
	getContentManager().registerOntology(modelInferOntology);

	super.setup();
    }

    @Override
    protected void processCommand(final Object command) {

	try {
	    if (command instanceof CreateAgentCommand) {
		CreateWorkgroup cw = new CreateWorkgroup();
		cw.setAlunoId(((CreateAgentCommand) command).getAlunoId()
			.toString());

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(frankOntology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		// TODO Deveria procurar interface no ambiente
		AID interfaceAID = new AID(AgentPrefixEnum.MANAGER.toString(),
			AID.ISLOCALNAME);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, cw));
		msg.addReceiver(interfaceAID);
		send(msg);
		releaseCommand(command);

	    } else if (command instanceof DestroyAgentCommand) {
		DestroyWorkgroup cw = new DestroyWorkgroup();
		cw.setAlunoId(((DestroyAgentCommand) command).getAlunoId()
			.toString());

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(frankOntology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

		// TODO Deveria procurar interface no ambiente
		AID interfaceAID = new AID(AgentPrefixEnum.MANAGER.toString(),
			AID.ISLOCALNAME);

		getContentManager().fillContent(msg,
			new Action(interfaceAID, cw));
		msg.addReceiver(interfaceAID);
		send(msg);
		releaseCommand(command);

	    } else if (command instanceof ProcessQuestionnaireCommand) {
		// TODO Deveria procurar interface no ambiente
		AID workgroupAID = new AID(AgentPrefixEnum.MANAGER.toString(),
			AID.ISLOCALNAME);

		ProcessQuestionnaireCommand processCommand = (ProcessQuestionnaireCommand) command;
		String alunoId = processCommand.getAlunoId().toString();

		Questionnaire questionnaire = convertToOntology(processCommand);

		SendQuestionnaire sendQuestionnaire = new SendQuestionnaire();
		sendQuestionnaire.setStudentId(alunoId);
		sendQuestionnaire.setQuestionnaire(questionnaire);

		ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
		msg.setLanguage(codec.getName());
		msg.setOntology(modelInferOntology.getName());
		msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
		msg.addReceiver(workgroupAID);

		getContentManager().fillContent(msg,
			new Action(workgroupAID, sendQuestionnaire));

		send(msg);

		MessageTemplate mt = MessageTemplate.MatchSender(workgroupAID);

		addBehaviour(new ReceiveMessageBehaviour(this, 1000, mt) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void handle(ACLMessage msg) {

			if (msg != null) {
			    if (ACLMessage.FAILURE == msg.getPerformative()) {
				System.out
					.println("Questionário não Processado...");
			    } else if (ACLMessage.INFORM == msg
				    .getPerformative()) {
				System.out
					.println("Questionário Processado com Sucesso!");
			    }

			}
			
			if (done()) {
			    releaseCommand(command);
			}

		    }
		});

	    } else if (command instanceof RequestCognitiveModelCommand) {

		final RequestCognitiveModelCommand requestCognitiveModel = (RequestCognitiveModelCommand) command;
		String alunoId = requestCognitiveModel.getAlunoId().toString();
		System.out.println("Recuperar Modelo Cognitivo " + alunoId);
		// TODO Deveria fazer lookup do workgroup do aluno no
		// ambiente
		AID workgroupAID = new AID(AgentPrefixEnum.WORKGROUP + alunoId,
			AID.ISLOCALNAME);

		ACLMessage requestModelMsg = new ACLMessage(ACLMessage.REQUEST);
		requestModelMsg.setLanguage(codec.getName());
		requestModelMsg.setOntology(modelInferOntology.getName());
		requestModelMsg.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
		requestModelMsg.addReceiver(workgroupAID);

		send(requestModelMsg);

		MessageTemplate mt = MessageTemplate.MatchSender(workgroupAID);

		addBehaviour(new ReceiveMessageBehaviour(this, 1000, mt) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void handle(ACLMessage msg) {
			System.out.print("Mensagem Recebida");

			if (msg != null) {
			    if (ACLMessage.FAILURE == msg.getPerformative()) {
				requestCognitiveModel.setCognitiveModel(null);

			    } else {
				try {
				    Result result = (Result) getContentManager()
					    .extractContent(msg);
				    CognitiveModel cognitiveModel = (CognitiveModel) result
					    .getValue();
				    requestCognitiveModel
					    .setCognitiveModel(cognitiveModel);

				} catch (UngroundedException e) {
				    e.printStackTrace();
				} catch (CodecException e) {
				    e.printStackTrace();
				} catch (OntologyException e) {
				    e.printStackTrace();
				}

			    }
			}

			if (done()) {
			    releaseCommand(command);
			}
		    }
		});

	    }

	} catch (CodecException e) {
	    e.printStackTrace();
	} catch (OntologyException e) {
	    e.printStackTrace();
	}

    }

    @SuppressWarnings("serial")
    class WaitServerResponse extends WakerBehaviour {
	WaitServerResponse(Agent a, Integer timeout) {
	    super(a, timeout);
	}

	protected void handleElapsedTimeout() {
	    System.out
		    .println("\n\tNo response from server. Please, try later!");
	}
    }

    private Questionnaire convertToOntology(ProcessQuestionnaireCommand command) {
	Questionnaire questionnaire = new Questionnaire();
	questionnaire.setName(command.getAlunoId() + "-" + command.getName());

	List learningDimensions = new ArrayList();
	for (DimensionCommand dimensionCommand : command.getDimensions()) {
	    LearningDimension learningDimension = new LearningDimension();
	    learningDimension.setDimension(dimensionCommand.getDimension());

	    List answers = convertToAnswerList(dimensionCommand);
	    learningDimension.setAnswers(answers);
	    learningDimensions.add(learningDimension);
	}

	questionnaire.setLearningDimensions(learningDimensions);

	return questionnaire;
    }

    private List convertToAnswerList(DimensionCommand dimensionCommand) {
	List answers = new ArrayList();
	for (AnswerCommand answerCommand : dimensionCommand.getAnswers()) {
	    Answer answer = new Answer();
	    answer.setOption(answerCommand.getOption());
	    answers.add(answer);
	}
	return answers;
    }

}
