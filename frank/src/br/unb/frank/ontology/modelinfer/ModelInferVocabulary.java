package br.unb.frank.ontology.modelinfer;

public interface ModelInferVocabulary {

    // Concept Student
    public static final String STUDENT = "Student";
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_NAME = "name";

    // Concept CognitiveModel
    public static final String COGNITIVE_MODEL = "CognitiveModel";
    public static final String COGNITIVE_MODEL_PERFORMANCE = "performance";
    public static final String COGNITIVE_MODEL_LEARNINGSTYLE = "learningStyle";
    public static final String COGNITIVE_MODEL_QUESTIONNAIRE = "questionnaire";

    // Concept Questionnaire
    public static final String QUESTIONNAIRE = "Questionnaire";
    public static final String QUESTIONNAIRE_NAME = "name";
    public static final String QUESTIONNAIRE_LEARNINGDIMENSIONS = "learningDimensions";

    // Concept Learning Dimensions
    public static final String LEARNING_DIMENSION = "LearningDimension";
    public static final String LEARNING_DIMENSION_DIMENSION = "dimension";
    public static final String LEARNING_DIMENSION_ANSWERS = "answers";

    // Concept Answer
    public static final String ANSWER = "Answer";
    public static final String ANSWER_OPTION = "option";

    // Predicate Owns
    public static final String OWNS = "Owns";
    public static final String OWNS_STUDENT = "student";
    public static final String OWNS_COGNITIVEMODEL = "cognitiveModel";

    // Action SendQuestionnaire
    public static final String SEND_QUESTIONNAIRE = "SendQuestionnaire";
    public static final String SEND_QUESTIONNAIRE_STUDENTID = "studentId";
    public static final String SEND_QUESTIONNAIRE_QUESTIONNAIRE = "questionnaire";

}
