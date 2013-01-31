package br.unb.frank.session.pergunta;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("perguntaList")
public class PerguntaList extends EntityQuery<Pergunta> {

    private static final long serialVersionUID = 1L;

    private static final String EJBQL = "select pergunta from Pergunta pergunta";

    private static final String[] RESTRICTIONS = { "lower(pergunta.titulo) like lower(concat(#{perguntaList.pergunta.titulo},'%'))", };

    private Pergunta pergunta = new Pergunta();

    public PerguntaList() {
	setEjbql(EJBQL);
	setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
	setMaxResults(25);
    }

    public Pergunta getPergunta() {
	return pergunta;
    }
}
