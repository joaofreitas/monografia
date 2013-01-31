package br.unb.frank.session.dimensao;

import br.unb.frank.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("dimensaoHome")
public class DimensaoHome extends EntityHome<Dimensao> {

    private static final long serialVersionUID = 1L;

    public void setDimensaoId(Integer id) {
	setId(id);
    }

    public Integer getDimensaoId() {
	return (Integer) getId();
    }

    @Override
    protected Dimensao createInstance() {
	Dimensao dimensao = new Dimensao();
	return dimensao;
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

    public Dimensao getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

    public List<Pergunta> getPerguntas() {
	return getInstance() == null ? null : new ArrayList<Pergunta>(
		getInstance().getPerguntas());
    }

}
