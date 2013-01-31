package br.unb.frank.session.resposta;

import br.unb.frank.entity.*;
import br.unb.frank.session.pergunta.PerguntaHome;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("respostaHome")
public class RespostaHome extends EntityHome<Resposta> {

    private static final long serialVersionUID = 1L;

    @In(create = true)
    PerguntaHome perguntaHome;

    public void setRespostaId(Integer id) {
	setId(id);
    }

    public Integer getRespostaId() {
	return (Integer) getId();
    }

    @Override
    protected Resposta createInstance() {
	Resposta resposta = new Resposta();
	return resposta;
    }

    public void load() {
	if (isIdDefined()) {
	    wire();
	}
    }

    public void wire() {
	getInstance();
	Pergunta pergunta = perguntaHome.getDefinedInstance();
	if (pergunta != null) {
	    getInstance().setPergunta(pergunta);
	}
    }

    public boolean isWired() {
	return true;
    }

    public Resposta getDefinedInstance() {
	return isIdDefined() ? getInstance() : null;
    }

}
