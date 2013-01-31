package br.unb.frank.business;

import java.util.ArrayList;
import java.util.List;

import br.unb.frank.domain.command.AnswerCommand;
import br.unb.frank.domain.command.DimensionCommand;
import br.unb.frank.domain.command.ProcessQuestionnaireCommand;
import br.unb.frank.entity.Dimensao;
import br.unb.frank.entity.Questionario;
import br.unb.frank.entity.Resposta;

public class JadeBusiness {

    public ProcessQuestionnaireCommand criarProcessQuestionnaireCommand(
	    Questionario questionario) {

	ProcessQuestionnaireCommand command = new ProcessQuestionnaireCommand();
	Long alunoId = (Long.valueOf(questionario.getAluno().getId()));
	command.setAlunoId(alunoId);

	List<DimensionCommand> dimensions = new ArrayList<DimensionCommand>();
	List<Resposta> respostas = questionario.getRespostas();

	for (Resposta resposta : respostas) {
	    AnswerCommand answer = new AnswerCommand();
	    answer.setOption(resposta.getOpcaoEscolhida());
	    Dimensao dimensao = resposta.getPergunta().getDimensao();
	    DimensionCommand dimension = recuperarDimensionEquivalente(
		    dimensao.getId(), dimensions);

	    if (dimension == null) {
		dimension = new DimensionCommand();
		dimensions.add(dimension);
	    }

	    dimension.setDimension(dimensao.getId());
	    dimension.addAnswer(answer);
	}

	command.setDimensions(dimensions);

	return command;
    }

    private DimensionCommand recuperarDimensionEquivalente(int id,
	    List<DimensionCommand> dimensions) {
	for (DimensionCommand dimension : dimensions) {
	    if (dimension.getDimension().equals(id)) {
		return dimension;
	    }
	}
	return null;
    }
}
