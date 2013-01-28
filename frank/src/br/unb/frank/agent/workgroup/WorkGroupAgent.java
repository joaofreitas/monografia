package br.unb.frank.agent.workgroup;

import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import br.unb.frank.domain.AgentPrefixEnum;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;

/**
 * Classe que representa o workgroup do aluno
 * 
 * @author Joao Paulo
 * 
 */
public class WorkGroupAgent extends Agent {

    private String alunoId;

    private static final long serialVersionUID = 1L;

    private AID affectiveAID;
    private AID cognitiveAID;
    private AID metacognitiveAID;

    private Ontology jadeOntology = JADEManagementOntology.getInstance();
    private Ontology modelInferOntology = ModelInferOntology.getInstance();

    MessageTemplate pattern;

    @Override
    protected void setup() {
	if (!isValidArguments()) {
	    doDelete();
	}

	getContentManager().registerLanguage(new SLCodec(),
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(jadeOntology);
	getContentManager().registerOntology(modelInferOntology);

	registerWorkgroup();
	setAlunoId(getArgument(0));

	AgentContainer c = getContainerController();
	createAuxiliarAgents(c);

	pattern = MessageTemplate.and(
		MessageTemplate.MatchPerformative(ACLMessage.INFORM),
		MessageTemplate.MatchOntology(modelInferOntology.getName()));

	addBehaviour(new CyclicBehaviour() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void action() {
		ACLMessage msg = receive(pattern);

		if (msg != null) {
		    System.out.println("Recebi uma mensagem!");
		}

		block();
	    }
	});

	super.setup();
    }

    private void registerWorkgroup() {
	DFAgentDescription dfd = new DFAgentDescription();

	dfd.setName(getAID());
	ServiceDescription sd = new ServiceDescription();
	sd.setType("workgroup");
	sd.setName(getLocalName());
	sd.setOwnership("workgroup");
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
	    agentContainer.createNewAgent(getAffectiveAID().getLocalName(),
		    AffectiveAgent.class.getName(), null);

	    agentContainer.createNewAgent(getCognitiveAID().getLocalName(),
		    CognitiveAgent.class.getName(), null);

	    agentContainer.createNewAgent(getMetacognitiveAID().getLocalName(),
		    MetacognitiveAgent.class.getName(), null);

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
    public void doDelete() {
	try {
	    DFService.deregister(this);
	} catch (Exception e) {
	}

	super.doDelete();
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

}
