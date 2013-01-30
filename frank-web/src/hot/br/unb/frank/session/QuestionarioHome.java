package br.unb.frank.session;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("questionarioHome")
public class QuestionarioHome extends EntityHome<Questionario> {

    @In(create = true)
    AlunoHome alunoHome;

    public void setQuestionarioId(Integer id) {
	setId(id);
    }

    public Integer getQuestionarioId() {
	return (Integer) getId();
    }

    @Override
    protected Questionario createInstance() {
	Questionario questionario = new Questionario();
	return questionario;
    }

    public void load() {
	if (isIdDefined()) {
	    wire();
	}
    }

    public void wire() {
	getInstance();
	Aluno aluno = alunoHome.getDefinedInstance();
	if (aluno != null) {
	    getInstance().setAluno(aluno);
	}
    }

    public boolean isWired() {
	return true;
    }

    public Questionario getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

}
