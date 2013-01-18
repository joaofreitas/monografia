package br.unb.frank.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import br.unb.frank.model.Usuario;

@Name("usuarioList")
public class UsuarioList extends EntityQuery<Usuario>
{
    public UsuarioList()
    {
        setEjbql("select usuario from Usuario usuario");
    }
}
