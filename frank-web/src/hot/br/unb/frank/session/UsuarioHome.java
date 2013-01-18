package br.unb.frank.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import br.unb.frank.model.Usuario;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {
    private static final long serialVersionUID = 1887508852142848578L;

    @RequestParameter
    Long usuarioId;

    @Override
    public Object getId() {
	if (usuarioId == null) {
	    return super.getId();
	} else {
	    return usuarioId;
	}
    }

    @Override
    @Begin
    public void create() {
	super.create();
    }

}
