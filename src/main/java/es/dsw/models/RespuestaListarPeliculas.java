package es.dsw.models;

import java.util.List;

public class RespuestaListarPeliculas {
    private List<PeliculaModel> peliculas;
    private boolean error;
    private String msgError;

    public RespuestaListarPeliculas() {
		super();
	}

	public RespuestaListarPeliculas(List<PeliculaModel> peliculas, boolean error, String msgError) {
        this.peliculas = peliculas;
        this.error = error;
        this.msgError = msgError;
    }

	public List<PeliculaModel> getPeliculas() {
		return peliculas;
	}

	// Getters y setters
	public void setPeliculas(List<PeliculaModel> peliculas) {
		this.peliculas = peliculas;
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
