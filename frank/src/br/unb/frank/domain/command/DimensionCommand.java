package br.unb.frank.domain.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DimensionCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer dimension;

    private List<AnswerCommand> answers;

    public Integer getDimension() {
	return dimension;
    }

    public void setDimension(Integer dimension) {
	this.dimension = dimension;
    }

    public List<AnswerCommand> getAnswers() {
	return answers;
    }

    public void setAnswers(List<AnswerCommand> answers) {
	this.answers = answers;
    }

    public void addAnswer(AnswerCommand answer) {
	if (getAnswers() == null) {
	    setAnswers(new ArrayList<AnswerCommand>());
	}
	getAnswers().add(answer);
    }

}
