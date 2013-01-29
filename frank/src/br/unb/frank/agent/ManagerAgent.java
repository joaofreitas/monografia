package br.unb.frank.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.MessageTemplate;
import br.unb.frank.behaviour.SendQuestionnaireBehaviour;
import br.unb.frank.behaviour.ManageWorkgroupBehaviour;
import br.unb.frank.ontology.frankmanagement.FrankManagementOntology;
import br.unb.frank.ontology.modelinfer.ModelInferOntology;

public class ManagerAgent extends Agent {

    private static final long serialVersionUID = 2714298832678014462L;

    MessageTemplate pattern;

    private Codec codec = new SLCodec();
    private Ontology managementOntology = FrankManagementOntology.getInstance();
    private Ontology modelInferOntology = ModelInferOntology.getInstance();
    private Ontology jadeOntology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);

	getContentManager().registerOntology(managementOntology);
	getContentManager().registerOntology(jadeOntology);
	getContentManager().registerOntology(modelInferOntology);

	pattern = MessageTemplate.MatchOntology(managementOntology.getName());

	addBehaviour(new ManageWorkgroupBehaviour(pattern,
		getContainerController(), getContentManager()));

	pattern = MessageTemplate.MatchOntology(modelInferOntology.getName());

	addBehaviour(new SendQuestionnaireBehaviour(pattern,
		getContentManager()));

	super.setup();
    }
}
