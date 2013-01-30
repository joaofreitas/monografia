package br.unb.frank.session;

import br.unb.frank.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("respostaHome")
public class RespostaHome extends EntityHome<Resposta> {

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
