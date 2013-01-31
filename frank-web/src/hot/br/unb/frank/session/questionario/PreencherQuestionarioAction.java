package br.unb.frank.session.questionario;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;

import br.unb.frank.entity.Pergunta;
import br.unb.frank.entity.Questionario;
import br.unb.frank.entity.Resposta;

@Name("preencherQuestionarioAction")
public class PreencherQuestionarioAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @In(required = false)
    @Out(required = false)
    Questionario questionario;

    @In(required = false)
    @Out(required = false)
    private List<Pergunta> listaPerguntas;

    @In
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public String initQuestionario() {
	questionario = createInstance();

	Query query = entityManager.createQuery("from Pergunta");
	setListaPerguntas(query.getResultList());
	for (Pergunta pergunta : getListaPerguntas()) {
	    Resposta resposta = new Resposta();
	    resposta.setPergunta(pergunta);

	    Set<Resposta> respostas = new HashSet<Resposta>();
	    respostas.add(resposta);

	    pergunta.setRespostas(respostas);
	}

	return "responderPerguntas";
    }

    protected Questionario createInstance() {
	Questionario questionario = new Questionario();
	return questionario;
    }

    public List<Pergunta> getListaPerguntas() {
	return listaPerguntas;
    }

    public void setListaPerguntas(List<Pergunta> listaPerguntas) {
	this.listaPerguntas = listaPerguntas;
    }

}
