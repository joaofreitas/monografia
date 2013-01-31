package br.unb.frank.session.aluno;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("alunoList")
public class AlunoList extends EntityQuery<Aluno> {

    private static final long serialVersionUID = 1L;

    private static final String EJBQL = "select aluno from Aluno aluno";

    private static final String[] RESTRICTIONS = {
	    "lower(aluno.nome) like lower(concat(#{alunoList.aluno.nome},'%'))",
	    "lower(aluno.login) like lower(concat(#{alunoList.aluno.login},'%'))",
	    "lower(aluno.password) like lower(concat(#{alunoList.aluno.password},'%'))", };

    private Aluno aluno = new Aluno();

    public AlunoList() {
	setEjbql(EJBQL);
	setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
	setMaxResults(25);
    }

    public Aluno getAluno() {
	return aluno;
    }
}
