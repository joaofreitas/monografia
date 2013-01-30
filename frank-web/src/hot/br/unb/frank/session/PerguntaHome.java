package br.unb.frank.session;

import br.unb.frank.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("perguntaHome")
public class PerguntaHome extends EntityHome<Pergunta> {

    @In(create = true)
    DimensaoHome dimensaoHome;

    public void setPerguntaId(Integer id) {
	setId(id);
    }

    public Integer getPerguntaId() {
	return (Integer) getId();
    }

    @Override
    protected Pergunta createInstance() {
	Pergunta pergunta = new Pergunta();
	return pergunta;
    }

    public void load() {
	if (isIdDefined()) {
	    wire();
	}
    }

    public void wire() {
	getInstance();
	Dimensao dimensao = dimensaoHome.getDefinedInstance();
	if (dimensao != null) {
	    getInstance().setDimensao(dimensao);
	}
    }

    public boolean isWired() {
	if (getInstance().getDimensao() == null)
	    return false;
	return true;
    }

    public Pergunta getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

    public List<Resposta> getRespostas() {
	return getInstance() == null ? null : new ArrayList<Resposta>(
		getInstance().getRespostas());
    }

}
