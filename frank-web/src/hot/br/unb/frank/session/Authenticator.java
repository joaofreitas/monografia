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

import br.unb.frank.domain.model.AnswerListMessage;
import br.unb.frank.domain.model.CreateAgentMessage;
import br.unb.frank.domain.model.DestroyAgentMessage;
import br.unb.frank.model.Usuario;

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

    @Out
    Usuario usuario;

    @SuppressWarnings("unchecked")
    public boolean authenticate() {
	log.info("authenticating {0}", credentials.getUsername());

	String username = credentials.getUsername();
	String password = credentials.getPassword();

	Query query = entityManager
		.createQuery(
			"from Usuario where login = :username and password = :password")
		.setParameter("username", username)
		.setParameter("password", password);

	List<Usuario> listaUsuario = query.getResultList();

	if (listaUsuario != null && listaUsuario.size() > 0) {
	    identity.addRole("admin");
	    usuario = listaUsuario.get(0);

	    sendCreateAgentMessage();

	    return true;
	}
	return false;
    }

    private void sendCreateAgentMessage() {
	try {
	    CreateAgentMessage command = new CreateAgentMessage();
	    command.setAlunoId(usuario.getId());
	    jadeGateway.execute(command);

	    AnswerListMessage msg = new AnswerListMessage();
	    msg.setAlunoId(usuario.getId());
	    jadeGateway.execute(msg);
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
	    DestroyAgentMessage command = new DestroyAgentMessage();
	    command.setAlunoId(usuario.getId());
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
