package br.unb.frank.session;

import br.unb.frank.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("docenteHome")
public class DocenteHome extends EntityHome<Docente> {

    public void setDocenteId(Integer id) {
	setId(id);
    }

    public Integer getDocenteId() {
	return (Integer) getId();
    }

    @Override
    protected Docente createInstance() {
	Docente docente = new Docente();
	return docente;
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

    public Docente getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

    public List<Turma> getTurmas() {
	return getInstance() == null ? null : new ArrayList<Turma>(
		getInstance().getTurmas());
    }

}
