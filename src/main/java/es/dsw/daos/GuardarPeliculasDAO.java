package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.PeliculaModel;

public class GuardarPeliculasDAO {
	private MySqlConnection mySqlConnection;

	public GuardarPeliculasDAO(MySqlConnection mySqlConnection) {
		super();
		this.mySqlConnection = mySqlConnection;
	}
	
	public int guardarPelicula(PeliculaModel pelicula, int idUser) {

		// Recoger ID del género
		ResultSet rsGenero = mySqlConnection.executeSelect(
			"SELECT IDGENRE_GF FROM genre_film WHERE GENRE_GF = '" + pelicula.getGenero() + "'");
		if (mySqlConnection.isError()) throw new RuntimeException(mySqlConnection.msgError());
		int idGenero = -1;
		try {
			idGenero = (rsGenero != null && rsGenero.next()) ? rsGenero.getInt(1) : -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		// Recoger ID del país
		ResultSet rsPais = mySqlConnection.executeSelect(
			"SELECT IDCOUNTRY_CF FROM country_film WHERE COUNTRY_CF = '" + pelicula.getPais() + "'");
		if (mySqlConnection.isError()) throw new RuntimeException(mySqlConnection.msgError());
		int idPais = -1; 
		try {
			idPais = (rsPais != null && rsPais.next()) ? rsPais.getInt(1) : -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		// Recoger ID del distribuidor
		ResultSet rsProductor = mySqlConnection.executeSelect(
			"SELECT IDPRODUCER_PF FROM producer_film WHERE PRODUCER_PF = '" + pelicula.getDistribuidor() + "'");
		if (mySqlConnection.isError()) throw new RuntimeException(mySqlConnection.msgError());
		int idProductor = -1;
		try {
			idProductor = (rsProductor != null && rsProductor.next()) ? rsProductor.getInt(1) : -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		// Si no se encontraron IDs, usar NULL para evitar violaciones de FK
		String fechaEstrenoValue = pelicula.getFechaEstreno().isEmpty() ? "NULL" : ("'" + pelicula.getFechaEstreno() + "'");
		String idProductorValue = (idProductor <= 0) ? "NULL" : String.valueOf(idProductor);
	
		String sql = "INSERT INTO repository_film (TITLE_RF, SYNOPSIS_RF, IDGENRE_RF, DIRECTOR_RF, " +
						"REPARTO_RF, ANIO_RF, DATEPREMIERE_RF, IDPRODUCER_RF, IDCOUNTRY_RF, S_IDUSER_CF) " +
						"VALUES ('" 
						+ pelicula.getTitulo() + "','" 
						+ pelicula.getSynopsis() + "'," 
						+ String.valueOf(idGenero) + ",'" 
						+ pelicula.getDirector() + "','" 
						+ pelicula.getReparto() + "'," 
						+ pelicula.getAnio() + "," 
						+ fechaEstrenoValue + "," 
						+ idProductorValue  + "," 
						+ String.valueOf(idPais) + ", " 
						+ String.valueOf(idUser) + ")";
	
		ResultSet rs = mySqlConnection.executeInsert(sql);
		if (mySqlConnection.isError()) throw new RuntimeException(mySqlConnection.msgError());
	
		try {
			return (rs != null && rs.next()) ? rs.getInt(1) : -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
