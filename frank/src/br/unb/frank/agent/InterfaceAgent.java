package br.unb.frank.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.behaviour.ManageWorkgroupBehaviour;
import br.unb.frank.ontology.frankmanagement.FrankManagementOntology;

public class InterfaceAgent extends Agent {

    private static final long serialVersionUID = 2714298832678014462L;

    MessageTemplate pattern;

    private Codec codec = new SLCodec();
    private Ontology frankOntology = FrankManagementOntology.getInstance();
    private Ontology jadeOntology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec);
	getContentManager().registerOntology(frankOntology);
	// Registering ontology of Jade Management
	getContentManager().registerOntology(jadeOntology);

	pattern = MessageTemplate.MatchOntology(frankOntology.getName());

	addBehaviour(new ManageWorkgroupBehaviour(pattern,
		getContainerController(), getContentManager()));

	super.setup();
    }
}
