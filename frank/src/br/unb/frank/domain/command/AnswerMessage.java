package br.unb.frank.domain.command;

import java.io.Serializable;

public class AnswerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer option;

    public Integer getOption() {
	return option;
    }

    public void setOption(Integer option) {
	this.option = option;
    }

}
