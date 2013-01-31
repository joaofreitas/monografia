package br.unb.frank.session.resposta;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("respostaList")
public class RespostaList extends EntityQuery<Resposta> {

    private static final long serialVersionUID = 1L;

    private static final String EJBQL = "select resposta from Resposta resposta";

    private static final String[] RESTRICTIONS = {};

    private Resposta resposta = new Resposta();

    public RespostaList() {
	setEjbql(EJBQL);
	setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
	setMaxResults(25);
    }

    public Resposta getResposta() {
	return resposta;
    }
}
