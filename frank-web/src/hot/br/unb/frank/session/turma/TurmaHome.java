package br.unb.frank.session.turma;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import br.unb.frank.entity.Aluno;
import br.unb.frank.entity.Turma;

@Name("turmaHome")
public class TurmaHome extends EntityHome<Turma> {

    private static final long serialVersionUID = 1L;

    public void setDocenteId(Integer id) {
	setId(id);
    }

    public Integer getDocenteId() {
	return (Integer) getId();
    }

    @Override
    protected Turma createInstance() {
	Turma turma = new Turma();
	return turma;
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

    public Turma getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

    public List<Aluno> getAlunos() {
	return getInstance() == null ? null : new ArrayList<Aluno>(
		getInstance().getAlunos());
    }

}
