package es.dsw.models;

public class RespuestaGuardarPelicula {
	private boolean error;
	private String msgError;
	
	public RespuestaGuardarPelicula(boolean error, String msgError) {
		super();
		this.error = error;
		this.msgError = msgError;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
}
