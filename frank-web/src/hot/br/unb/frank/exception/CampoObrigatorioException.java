package br.unb.frank.exception;

public class CampoObrigatorioException extends Exception {

    private static final long serialVersionUID = 1404749579393434075L;

    public CampoObrigatorioException() {

    }

    public CampoObrigatorioException(String msg) {
	super(msg);
    }

    public CampoObrigatorioException(String msg, Throwable t) {
	super(msg, t);
    }

}
