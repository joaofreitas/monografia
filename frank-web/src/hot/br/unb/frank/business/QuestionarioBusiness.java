package br.unb.frank.business;

import java.util.ArrayList;
import java.util.List;

import br.unb.frank.entity.Pergunta;
import br.unb.frank.entity.Resposta;
import br.unb.frank.exception.CampoObrigatorioException;

public class QuestionarioBusiness {

    public List<Resposta> validarRespostasQuestionario(
	    List<Pergunta> listaPerguntas) throws CampoObrigatorioException {

	List<Resposta> respostas = new ArrayList<Resposta>();
	for (Pergunta pergunta : listaPerguntas) {
	    Resposta resposta = pergunta.getRespostas().get(0);
	    if (resposta.getOpcaoEscolhida() == null) {
		throw new CampoObrigatorioException();
	    } else {
		respostas.add(resposta);
	    }
	}
	return respostas;
    }

}
