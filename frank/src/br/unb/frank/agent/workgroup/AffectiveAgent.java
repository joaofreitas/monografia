package br.unb.frank.agent.workgroup;

import jade.core.Agent;

public class AffectiveAgent extends Agent {

    private static final long serialVersionUID = 3124912678660617289L;
    
    @Override
    public void doDelete() {
	System.out.println("Terminating...");
        super.doDelete();
    }

}
