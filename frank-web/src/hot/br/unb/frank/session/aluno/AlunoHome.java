package br.unb.frank.session.aluno;

import br.unb.frank.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("alunoHome")
public class AlunoHome extends EntityHome<Aluno> {

    private static final long serialVersionUID = 1L;

    public void setAlunoId(Integer id) {
	setId(id);
    }

    public Integer getAlunoId() {
	return (Integer) getId();
    }

    @Override
    protected Aluno createInstance() {
	Aluno aluno = new Aluno();
	return aluno;
    }

    public void load() {
	if (isIdDefined()) {
	    wire();
	}
    }

    public void wire() {
	getInstance();
    }

    public boolean isWired() {
	return true;
    }

    public Aluno getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

    public List<Questionario> getQuestionarios() {
	return getInstance() == null ? null : new ArrayList<Questionario>(
		getInstance().getQuestionarios());
    }

}
