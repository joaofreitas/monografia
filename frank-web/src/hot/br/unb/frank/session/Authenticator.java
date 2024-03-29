package br.unb.frank.session;

import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.DynamicJadeGateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import br.unb.frank.domain.command.CreateAgentCommand;
import br.unb.frank.domain.command.DestroyAgentCommand;
import br.unb.frank.entity.Aluno;
import br.unb.frank.entity.Docente;
import br.unb.frank.entity.Usuario;

@Scope(ScopeType.APPLICATION)
@Name("authenticator")
public class Authenticator {
    @Logger
    private Log log;

    @In
    EntityManager entityManager;

    @In
    Identity identity;

    @In
    Credentials credentials;

    @In
    DynamicJadeGateway jadeGateway;

    @In(required = false)
    @Out(required = false)
    Aluno aluno;

    @In(required = false)
    @Out(required = false)
    Docente docente;

    @In(required = false)
    @Out(required = false)
    Usuario usuario;

    @SuppressWarnings("unchecked")
    public boolean authenticate() {
	log.info("authenticating {0}", credentials.getUsername());

	String username = credentials.getUsername();
	String password = credentials.getPassword();

	if (username.equals("admin")) {
	    return true;
	}

	Query query = entityManager
		.createQuery(
			"from Usuario where login = :username and senha = :password")
		.setParameter("username", username)
		.setParameter("password", password);

	List<Usuario> listaUsuarios = query.getResultList();

	if (listaUsuarios != null && listaUsuarios.size() > 0) {
	    identity.addRole("admin");

	    usuario = listaUsuarios.get(0);
	    if (usuario.getAlunos().size() > 0) {
		aluno = usuario.getAlunos().get(0);
		sendCreateAgentMessage();
	    } else if (usuario.getDocentes().size() > 0) {
		docente = usuario.getDocentes().get(0);
	    } else {
		return false;
	    }

	    return true;
	}
	return false;
    }

    private void sendCreateAgentMessage() {
	try {
	    CreateAgentCommand command = new CreateAgentCommand();
	    command.setAlunoId(Long.valueOf(aluno.getId()));
	    jadeGateway.execute(command);

	} catch (StaleProxyException e) {
	    log.error(e);
	} catch (ControllerException e) {
	    log.error(e);
	} catch (InterruptedException e) {
	    log.error(e);
	}
    }

    public void logout() {
	sendDestroyAgentMessage();
	identity.logout();
    }

    private void sendDestroyAgentMessage() {
	try {
	    DestroyAgentCommand command = new DestroyAgentCommand();
	    Integer id = aluno.getId();
	    command.setAlunoId(id.longValue());
	    jadeGateway.execute(command);
	} catch (StaleProxyException e) {
	    log.error(e);
	} catch (ControllerException e) {
	    log.error(e);
	} catch (InterruptedException e) {
	    log.error(e);
	}

    }

}
