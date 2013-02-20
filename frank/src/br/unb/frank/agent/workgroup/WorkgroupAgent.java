package br.unb.frank.agent.workgroup;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.KillAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import br.unb.frank.behaviour.RequestModelBehaviour;
import br.unb.frank.behaviour.WorkgroupBehaviour;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import br.unb.frank.ontology.modelinfer.predicate.Owns;

/**
 * Classe que representa o workgroup do aluno
 * 
 * @author Joao Paulo
 * 
 */
public class WorkgroupAgent extends Agent {

    private String alunoId;

    private static final long serialVersionUID = 1L;

    private AID affectiveAID;
    private AID cognitiveAID;
    private AID metacognitiveAID;
    MessageTemplate pattern;

    private Owns own = new Owns();

    @Override
    protected void setup() {
	if (!isValidArguments()) {
	    doDelete();
	}

	Ontology jadeOntology = JADEManagementOntology.getInstance();
	Ontology modelInferOntology = ModelInferOntology.getInstance();

	getContentManager().registerLanguage(new SLCodec(),
		FIPANames.ContentLanguage.FIPA_SL0);

	getContentManager().registerOntology(jadeOntology);
	getContentManager().registerOntology(modelInferOntology);

	setAlunoId(getArgument(0));
	registerAsWorkgroup();

	AgentContainer c = getContainerController();
	createAuxiliarAgents(c);

	pattern = MessageTemplate.and(
		MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
		MessageTemplate.MatchOntology(modelInferOntology.getName()));
	addBehaviour(new RequestModelBehaviour(pattern, own,
		getContentManager()));

	pattern = MessageTemplate.and(
		MessageTemplate.MatchPerformative(ACLMessage.INFORM),
		MessageTemplate.MatchOntology(modelInferOntology.getName()));

	addBehaviour(new WorkgroupBehaviour(pattern, getContentManager(),
		getAffectiveAID(), getCognitiveAID(), getMetacognitiveAID(),
		own));

	super.setup();
    }

    private void registerAsWorkgroup() {
	DFAgentDescription dfd = new DFAgentDescription();

	dfd.setName(getAID());
	ServiceDescription sd = new ServiceDescription();
	sd.setName(getLocalName());
	sd.setType("workgroup");
	sd.setOwnership("Frank-SMA");
	dfd.addServices(sd);
	try {
	    DFService.register(this, dfd);
	} catch (FIPAException fe) {
	    fe.printStackTrace();
	}
    }

    /**
     * Cria os agentes cognitivos, metacognitivos e afetivos
     * 
     * @param agentContainer
     */
    private void createAuxiliarAgents(AgentContainer agentContainer) {
	try {
	    generateIds();
	    AgentController affectiveAgent = agentContainer.createNewAgent(
		    getAffectiveAID().getLocalName(),
		    AffectiveAgent.class.getName(), null);
	    affectiveAgent.start();

	    AgentController cognitiveAgent = agentContainer.createNewAgent(
		    getCognitiveAID().getLocalName(),
		    CognitiveAgent.class.getName(), null);
	    cognitiveAgent.start();

	    AgentController metacognitiveAgent = agentContainer.createNewAgent(
		    getMetacognitiveAID().getLocalName(),
		    MetacognitiveAgent.class.getName(), null);
	    metacognitiveAgent.start();

	} catch (StaleProxyException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Gera os ids para os agentes cognitivos, metacognitivos e afetivos com
     * base no id do aluno
     */
    private void generateIds() {
	affectiveAID = new AID(AgentPrefixEnum.AFFECTIVE + getAlunoId(),
		AID.ISLOCALNAME);
	cognitiveAID = new AID(AgentPrefixEnum.COGNITIVE + getAlunoId(),
		AID.ISLOCALNAME);
	metacognitiveAID = new AID(
		AgentPrefixEnum.METACOGNITIVE + getAlunoId(), AID.ISLOCALNAME);
    }

    /**
     * Verifica se foi informado o id do aluno
     * 
     * @return
     */
    private boolean isValidArguments() {
	return getArguments().length > 0;
    }

    /**
     * Retorna o argumento <index> enviado por parâmetro
     * 
     * @param index
     * @return
     */
    private String getArgument(Integer index) {
	return (String) getArguments()[index];
    }

    @Override
    protected void takeDown() {
	try {
	    DFService.deregister(this);
	    destroyAgent(getCognitiveAID());
	    destroyAgent(getMetacognitiveAID());
	    destroyAgent(getAffectiveAID());

	} catch (FIPAException e) {
	    e.printStackTrace();
	} catch (CodecException e) {
	    e.printStackTrace();
	} catch (OntologyException e) {
	    e.printStackTrace();
	}

	super.takeDown();
    }

    /**
     * GETTERS AND SETTERS
     */

    public String getAlunoId() {
	return alunoId;
    }

    public void setAlunoId(String alunoId) {
	this.alunoId = alunoId;
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

    public void destroyAgent(AID killedAID) throws CodecException,
	    OntologyException {

	KillAgent ka = new KillAgent();
	ka.setAgent(killedAID);
	Action kaction = new Action();
	kaction.setActor(getAMS());
	kaction.setAction(ka);

	ACLMessage AMSRequest = new ACLMessage(ACLMessage.REQUEST);
	AMSRequest.setSender(getAID());
	AMSRequest.clearAllReceiver();
	AMSRequest.addReceiver(getAMS());
	AMSRequest.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
	AMSRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);

	AMSRequest
		.setOntology(jade.domain.JADEAgentManagement.JADEManagementOntology.NAME);
	getContentManager().fillContent(AMSRequest, kaction);

	send(AMSRequest);
    }

}
