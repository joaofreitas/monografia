package br.unb.frank.domain.command;

import java.io.Serializable;
import java.util.List;

public class AnswerListMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long alunoId;

    private List<AnswerMessage> listAnswer;

    public Long getAlunoId() {
	return alunoId;
    }

    public void setAlunoId(Long alunoId) {
	this.alunoId = alunoId;
    }

    public List<AnswerMessage> getListAnswer() {
	return listAnswer;
    }

    public void setListAnswer(List<AnswerMessage> listAnswer) {
	this.listAnswer = listAnswer;
    }

}
