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
			"from Aluno where login = :username and password = :password")
		.setParameter("username", username)
		.setParameter("password", password);

	List<Aluno> listaAlunos = query.getResultList();

	if (listaAlunos != null && listaAlunos.size() > 0) {
	    identity.addRole("admin");
	    aluno = listaAlunos.get(0);
	    sendCreateAgentMessage();

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
//	sendDestroyAgentMessage();
	identity.logout();
    }

    private void sendDestroyAgentMessage() {
	try {
	    DestroyAgentCommand command = new DestroyAgentCommand();
	    // command.setAlunoId(usuario.getId());
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
