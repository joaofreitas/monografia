package br.unb.frank.session.turma;

import java.io.Serializable;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;

import br.unb.frank.entity.Turma;

@Name("turmaAction")
public class TurmaAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @In(required = false)
    @Out(required = false)
    Turma turma;

    public String detalharTurma(Turma turma) {
	this.turma = turma;
	return "detalharTurma";
    }

}
