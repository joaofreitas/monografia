package br.unb.frank.domain.command;

import java.io.Serializable;
import java.util.List;

public class ProcessQuestionnaireCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long alunoId;
    private String name;
    private List<DimensionCommand> dimensions;

    private DimensionCommand result;

    public Long getAlunoId() {
	return alunoId;
    }

    public void setAlunoId(Long alunoId) {
	this.alunoId = alunoId;
    }

    public List<DimensionCommand> getDimensions() {
	return dimensions;
    }

    public void setDimensions(List<DimensionCommand> dimensions) {
	this.dimensions = dimensions;
    }

    public DimensionCommand getResult() {
	return result;
    }

    public void setResult(DimensionCommand result) {
	this.result = result;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
