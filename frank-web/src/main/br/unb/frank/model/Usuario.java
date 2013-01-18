package br.unb.frank.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    // seam-gen attributes (you should probably edit these)
    private Long id;
    private String login;
    private String password;

    // add additional entity attributes

    // seam-gen attribute getters/setters with annotations (you probably should
    // edit)

    @Id
    @GeneratedValue
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Size(max = 255)
    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    @Size(max = 255)
    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
