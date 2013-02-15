package br.unb.frank.agent.workgroup;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.behaviour.CognitiveInferenceBehaviour;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;

public class CognitiveAgent extends Agent {

    private static final long serialVersionUID = 3779144729348812061L;

    private Codec codec = new SLCodec();
    private Ontology jadeOntology = JADEManagementOntology.getInstance();
    private Ontology modelInferOntology = ModelInferOntology.getInstance();

    MessageTemplate pattern;

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(jadeOntology);
	getContentManager().registerOntology(modelInferOntology);

	pattern = MessageTemplate.and(
		MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
		MessageTemplate.MatchOntology(modelInferOntology.getName()));

	addBehaviour(new CognitiveInferenceBehaviour(pattern, getContentManager(),
		codec, modelInferOntology));

	super.setup();
    }
}
