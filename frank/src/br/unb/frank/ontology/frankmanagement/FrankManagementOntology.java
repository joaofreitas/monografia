package br.unb.frank.ontology.frankmanagement;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;
import br.unb.frank.ontology.frankmanagement.action.CreateWorkgroup;
import br.unb.frank.ontology.frankmanagement.action.DestroyWorkgroup;

public class FrankManagementOntology extends Ontology implements
	FrankManagementVocabulary {

    private static final long serialVersionUID = 1L;
    public static final String ONTOLOGY_NAME = "Frank-Ontology";
    private static Ontology instance = new FrankManagementOntology();

    private FrankManagementOntology() {
	super(ONTOLOGY_NAME, BasicOntology.getInstance());

	try {

	    AgentActionSchema as = new AgentActionSchema(CREATE_WORKGROUP);
	    add(as, CreateWorkgroup.class);
	    as.add(CREATE_WORKGROUP_ALUNOID,
		    (PrimitiveSchema) getSchema(BasicOntology.STRING),
		    ObjectSchema.MANDATORY);

	    as = new AgentActionSchema(DESTROY_WORKGROUP);
	    add(as, DestroyWorkgroup.class);
	    as.add(DESTROY_WORKGROUP_ALUNOID,
		    (PrimitiveSchema) getSchema(BasicOntology.STRING),
		    ObjectSchema.MANDATORY);

	} catch (OntologyException e) {
	    e.printStackTrace();
	}
    }

    public static Ontology getInstance() {
	return instance;
    }

}
