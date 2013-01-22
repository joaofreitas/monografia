package br.unb.frank.session;

import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.DynamicJadeGateway;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import br.unb.frank.domain.model.DestroyAgentMessage;
import br.unb.frank.model.Usuario;

@Scope(ScopeType.APPLICATION)
@Name("logoutAction")
public class LogoutAction {
    @Logger
    private Log log;

    @In
    DynamicJadeGateway jadeGateway;

    @In
    Identity identity;

    @In
    Usuario usuario;

    public void logout() {
	sendDestroyAgentMessage();
	identity.logout();
    }

    public void sendDestroyAgentMessage() {
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
