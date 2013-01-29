package br.unb.frank.domain.command;

import java.io.Serializable;

public class DestroyAgentCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long alunoId;

    public Long getAlunoId() {
	return alunoId;
    }

    public void setAlunoId(Long alunoId) {
	this.alunoId = alunoId;
    }

}
