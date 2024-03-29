package br.unb.frank.initializer;

import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.DynamicJadeGateway;
import jade.wrapper.gateway.JadeGateway;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.log.Log;

import br.unb.frank.agent.GatewayFrankAgent;

@Name("createGatewayAgent")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Startup
public class CreateGatewayAgent {

    @Logger
    private Log log;

    @Out
    DynamicJadeGateway jadeGateway;

    @Create
    public void ping() {
	log.info("----------------	Criando Gateway	--------------");
	iniciarJadeContainer();
	log.info("----------------	Finalizando 	--------------");
    }

    @Asynchronous
    public void iniciarJadeContainer() {

	Properties pp = new Properties();
	pp.setProperty(Profile.MAIN_HOST, "localhost");
	pp.setProperty(Profile.MAIN_PORT, "1099");
	pp.setProperty(Profile.CONTAINER_NAME, "Seam-Container");

	jadeGateway = JadeGateway.getDefaultGateway();

	log.info("----------------	Configurando	--------------");
	jadeGateway.init(GatewayFrankAgent.class.getName(), pp);

    }

}
