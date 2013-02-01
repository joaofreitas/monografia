package br.unb.frank.session.questionario;

import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.DynamicJadeGateway;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessages;

import br.unb.frank.business.JadeBusiness;
import br.unb.frank.business.QuestionarioBusiness;
import br.unb.frank.domain.command.ProcessQuestionnaireCommand;
import br.unb.frank.entity.Aluno;
import br.unb.frank.entity.Pergunta;
import br.unb.frank.entity.Questionario;
import br.unb.frank.entity.Resposta;
import br.unb.frank.exception.CampoObrigatorioException;

@Name("preencherQuestionarioAction")
public class PreencherQuestionarioAction implements Serializable {

    private static final long serialVersionUID = 1L;

    QuestionarioBusiness questionarioBusiness = new QuestionarioBusiness();

    JadeBusiness jadeBusiness = new JadeBusiness();

    @In
    public StatusMessages statusMessages;

    @In(required = false)
    @Out(required = false)
    Questionario questionario;

    @In(required = false)
    @Out(required = false)
    private List<Pergunta> listaPerguntas;

    @In(required = false)
    @Out(required = false)
    Aluno aluno;

    @In
    EntityManager entityManager;

    @In
    DynamicJadeGateway jadeGateway;

    public String initQuestionario() {
	return "responderPerguntas";
    }

    public String processarQuestionario() {
	try {
	    List<Resposta> respostas = questionarioBusiness
		    .validarRespostasQuestionario(listaPerguntas);
	    questionario.setAluno(aluno);
	    questionario.setRespostas(respostas);
	    questionario.setNome("q-" + aluno.getNome()
		    + Calendar.getInstance().getTime());

	    ProcessQuestionnaireCommand command = jadeBusiness
		    .criarProcessQuestionnaireCommand(questionario);

	    try {
		jadeGateway.execute(command);
	    } catch (StaleProxyException e) {
		e.printStackTrace();
	    } catch (ControllerException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	} catch (CampoObrigatorioException e) {
	    e.printStackTrace();
	    statusMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,
		    "É Obrigatório o Preenchimento de Todas as Perguntas");
	}

	return "sucesso";

    }

    @SuppressWarnings("unchecked")
    public List<Pergunta> getListaPerguntas() {

	if (listaPerguntas == null) {
	    questionario = new Questionario();

	    Query query = entityManager.createQuery("from Pergunta");
	    listaPerguntas = query.getResultList();
	    for (Pergunta pergunta : listaPerguntas) {
		Resposta resposta = new Resposta();
		resposta.setQuestionario(questionario);
		resposta.setPergunta(pergunta);

		List<Resposta> respostas = new ArrayList<Resposta>();
		respostas.add(resposta);

		pergunta.setRespostas(respostas);
	    }
	}

	return listaPerguntas;
    }

    public void setListaPerguntas(List<Pergunta> listaPerguntas) {
	this.listaPerguntas = listaPerguntas;
    }

}
