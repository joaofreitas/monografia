package br.unb.frank.agent.workgroup;

import br.unb.frank.ontology.modelinfer.ModelInferOntology;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;

public class MetacognitiveAgent extends Agent {

    private static final long serialVersionUID = 1L;

    private Codec codec = new SLCodec();
    private Ontology jadeOntology = JADEManagementOntology.getInstance();
    private Ontology modelInferOntology = ModelInferOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(jadeOntology);
	getContentManager().registerOntology(modelInferOntology);

	super.setup();
    }

}
