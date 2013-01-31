package br.unb.frank.session.docente;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("docenteList")
public class DocenteList extends EntityQuery<Docente> {

    private static final long serialVersionUID = 1L;

    private static final String EJBQL = "select docente from Docente docente";

    private static final String[] RESTRICTIONS = {
	    "lower(docente.nome) like lower(concat(#{docenteList.docente.nome},'%'))",
	    "lower(docente.login) like lower(concat(#{docenteList.docente.login},'%'))",
	    "lower(docente.password) like lower(concat(#{docenteList.docente.password},'%'))", };

    private Docente docente = new Docente();

    public DocenteList() {
	setEjbql(EJBQL);
	setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
	setMaxResults(25);
    }

    public Docente getDocente() {
	return docente;
    }
}
