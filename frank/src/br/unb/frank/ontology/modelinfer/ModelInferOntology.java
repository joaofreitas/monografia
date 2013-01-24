package br.unb.frank.ontology.modelinfer;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

public class ModelInferOntology extends Ontology implements
	ModelInferVocabulary {

    private static final long serialVersionUID = 1L;

    public static final String ONTOLOGY_NAME = "ModelInfer-ontology";
    private static Ontology instance = new ModelInferOntology();

    private ModelInferOntology() {
	super(ONTOLOGY_NAME, BasicOntology.getInstance());

	try {

	    // CONCEPTS
	    // Structuring student
	    ConceptSchema cs = (ConceptSchema) getSchema(STUDENT);
	    cs.add(STUDENT_ID,
		    (PrimitiveSchema) getSchema(BasicOntology.STRING),
		    ObjectSchema.MANDATORY);
	    cs.add(STUDENT_NAME,
		    (PrimitiveSchema) getSchema(BasicOntology.STRING),
		    ObjectSchema.MANDATORY);

	    // Structuring cognitive model
	    cs = (ConceptSchema) getSchema(COGNITIVE_MODEL);
	    cs.add(COGNITIVE_MODEL_PERFORMANCE,
		    (PrimitiveSchema) getSchema(BasicOntology.FLOAT),
		    ObjectSchema.MANDATORY);
	    cs.add(COGNITIVE_MODEL_LEARNING_STYLE,
		    (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
		    ObjectSchema.MANDATORY);

	    // TODO Structure metacognitive model
	    // TODO Structure affectivecognitive model

	    // Structuring answer
	    cs = (ConceptSchema) getSchema(ANSWER);
	    cs.add(ANSWER_HEIGHT,
		    (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
		    ObjectSchema.MANDATORY);
	    cs.add(ANSWER_OPTION,
		    (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
		    ObjectSchema.MANDATORY);

	    // Structuring questionnaire
	    cs = (ConceptSchema) getSchema(QUESTIONNAIRE);
	    cs.add(QUESTIONNAIRE_ANSWER, (PrimitiveSchema) getSchema(ANSWER),
		    1, ObjectSchema.UNLIMITED);
	    cs.add(QUESTIONNAIRE_NAME,
		    (PrimitiveSchema) getSchema(BasicOntology.STRING),
		    ObjectSchema.MANDATORY);

	    // PREDICATE
	    // Structuring owns
	    PredicateSchema ps = (PredicateSchema) getSchema(OWNS);
	    ps.add(OWNS_STUDENT, (ConceptSchema) getSchema(STUDENT));
	    ps.add(OWNS_COGNITIVE_MODEL,
		    (ConceptSchema) getSchema(COGNITIVE_MODEL));

	    // ACTION
	    // Structuring send questionnaire
	    AgentActionSchema as = (AgentActionSchema) getSchema(SEND_QUESTIONNAIRE);
	    as.add(SEND_QUESTIONNAIRE_QUESTIONNAIRE,
		    (ConceptSchema) getSchema(QUESTIONNAIRE));
	    as.add(SEND_QUESTIONNAIRE_DESTINY,
		    (ConceptSchema) getSchema(BasicOntology.AID));

	} catch (OntologyException e) {
	    e.printStackTrace();
	}
    }

    public static Ontology getInstance() {
	return instance;
    }

}
