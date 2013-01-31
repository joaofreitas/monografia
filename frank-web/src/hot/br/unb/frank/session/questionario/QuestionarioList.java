package br.unb.frank.session.questionario;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("questionarioList")
public class QuestionarioList extends EntityQuery<Questionario> {

    private static final long serialVersionUID = 1L;

    private static final String EJBQL = "select questionario from Questionario questionario";

    private static final String[] RESTRICTIONS = { "lower(questionario.nome) like lower(concat(#{questionarioList.questionario.nome},'%'))", };

    private Questionario questionario = new Questionario();

    public QuestionarioList() {
	setEjbql(EJBQL);
	setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
	setMaxResults(25);
    }

    public Questionario getQuestionario() {
	return questionario;
    }
}
