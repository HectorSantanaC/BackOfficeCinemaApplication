package es.dsw.models;

public class PeliculaModel {
  private String titulo;
  private String synopsis;
  private String genero;
  private String director;
  private String reparto;
  private String anio;
  private String fechaEstreno;
  private String distribuidor;
  private String pais;

  public PeliculaModel() {
	  super();
  }
  
    public PeliculaModel(String titulo, String synopsis, String genero, String director, String reparto, String anio,
		String fechaEstreno, String distribuidor, String pais) {
	super();
	this.titulo = titulo;
	this.synopsis = synopsis;
	this.genero = genero;
	this.director = director;
	this.reparto = reparto;
	this.anio = anio;
	this.fechaEstreno = fechaEstreno;
	this.distribuidor = distribuidor;
	this.pais = pais;
}

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getReparto() {
    return reparto;
  }

  public void setReparto(String reparto) {
    this.reparto = reparto;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public String getFechaEstreno() {
    return fechaEstreno;
  }

  public void setFechaEstreno(String fechaEstreno) {
    this.fechaEstreno = fechaEstreno;
  }

  public String getDistribuidor() {
    return distribuidor;
  }

  public void setDistribuidor(String distribuidor) {
    this.distribuidor = distribuidor;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }
}
