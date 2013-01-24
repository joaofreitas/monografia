package br.unb.frank.agent.workgroup;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.JADEAgentManagement.JADEManagementOntology;

public class MetacognitiveAgent extends Agent {

    private static final long serialVersionUID = -988858595192916443L;
    private Codec codec = new SLCodec();
    private Ontology jadeOntology = JADEManagementOntology.getInstance();

    @Override
    protected void setup() {

	getContentManager().registerLanguage(codec,
		FIPANames.ContentLanguage.FIPA_SL0);
	getContentManager().registerOntology(jadeOntology);

	registerMetacognitive();
	super.setup();
    }

    private void registerMetacognitive() {
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

    @Override
    public void doDelete() {
	try {
	    DFService.deregister(this);
	} catch (Exception e) {
	}
	super.doDelete();
    }

}
