package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.PeliculaModel;

public class PeliculasDAO {

	private MySqlConnection mySqlConnection;

	public PeliculasDAO() {
		mySqlConnection = new MySqlConnection();
	}

	public List<PeliculaModel> listarPeliculas() {
		
		List<PeliculaModel> peliculas = new ArrayList<>();
		
		mySqlConnection.open();

		if (!mySqlConnection.isError()) {

			String sql = "SELECT TITLE_RF, SYNOPSIS_RF, GENRE_GF, DIRECTOR_RF, REPARTO_RF, " 
							  + "ANIO_RF, DATEPREMIERE_RF, PRODUCER_PF, COUNTRY_CF "
					   + "FROM db_filmcinema.repository_film "
							+ "INNER JOIN db_filmcinema.genre_film ON IDGENRE_GF = IDGENRE_RF "
							+ "INNER JOIN db_filmcinema.producer_film ON IDPRODUCER_PF = IDPRODUCER_RF "
							+ "INNER JOIN db_filmcinema.country_film ON IDCOUNTRY_CF = IDCOUNTRY_RF ";

			ResultSet rs = mySqlConnection.executeSelect(sql);
		

			try {
				while (rs.next()) {
					
					PeliculaModel pelicula = new PeliculaModel();
					
					pelicula.setTitulo(rs.getString("TITLE_RF"));
					pelicula.setSynopsis(rs.getString("SYNOPSIS_RF"));
					pelicula.setGenero(rs.getString("GENRE_GF"));
					pelicula.setDirector(rs.getString("DIRECTOR_RF"));
					pelicula.setReparto(rs.getString("REPARTO_RF"));
					pelicula.setAnio(rs.getString("ANIO_RF"));
					pelicula.setFechaEstreno(rs.getString("DATEPREMIERE_RF"));
					pelicula.setDistribuidor(rs.getString("PRODUCER_PF"));
					pelicula.setPais(rs.getString("COUNTRY_CF"));
					
					peliculas.add(pelicula);
				}
				
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		
		return peliculas;
	}
}
	

