package br.unb.frank.session;

import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.DynamicJadeGateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import br.unb.frank.domain.model.CreateAgentMessage;
import br.unb.frank.model.Usuario;

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
	    sendGatewayMessage(listaUsuario);
	    identity.addRole("admin");

	    return true;
	}
	return false;
    }

    private void sendGatewayMessage(List<Usuario> listaUsuario) {
	try {
	    Usuario usuario = listaUsuario.get(0);
	    CreateAgentMessage command = new CreateAgentMessage();
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
