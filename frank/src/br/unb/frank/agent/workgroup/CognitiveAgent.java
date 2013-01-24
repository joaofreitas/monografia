package br.unb.frank.agent.workgroup;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;

public class CognitiveAgent extends Agent {

    private static final long serialVersionUID = 3779144729348812061L;
    private Codec codec = new SLCodec();
    private Ontology jadeOntology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec);
	getContentManager().registerOntology(jadeOntology);
	super.setup();
    }
}
