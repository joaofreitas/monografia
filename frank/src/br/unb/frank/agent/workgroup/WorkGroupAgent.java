package br.unb.frank.agent.workgroup;

import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import br.unb.frank.domain.AgentPrefixEnum;

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

    @Override
    protected void setup() {
	if (!isValidArguments()) {
	    doDelete();
	}

	setAlunoId(getArgument(0));
	System.out.println("Criarei agentes do aluno " + getAlunoId());
	AgentContainer c = getContainerController();
	createAuxiliarAgents(c);

	super.setup();
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
	metacognitiveAID = new AID(AgentPrefixEnum.METACOGNITIVE + getAlunoId(),
		AID.ISLOCALNAME);
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
