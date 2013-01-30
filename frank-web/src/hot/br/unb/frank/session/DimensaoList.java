package br.unb.frank.session;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("dimensaoList")
public class DimensaoList extends EntityQuery<Dimensao> {

    private static final String EJBQL = "select dimensao from Dimensao dimensao";

    private static final String[] RESTRICTIONS = {
	    "lower(dimensao.nome) like lower(concat(#{dimensaoList.dimensao.nome},'%'))",
	    "lower(dimensao.descricao) like lower(concat(#{dimensaoList.dimensao.descricao},'%'))", };

    private Dimensao dimensao = new Dimensao();

    public DimensaoList() {
	setEjbql(EJBQL);
	setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
	setMaxResults(25);
    }

    public Dimensao getDimensao() {
	return dimensao;
    }
}
