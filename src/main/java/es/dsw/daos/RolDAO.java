package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.dsw.connections.MySqlConnection;

public class RolDAO {

	private MySqlConnection mySqlConnection;

	public RolDAO() {
		mySqlConnection = new MySqlConnection();
	}
	
	public List<String> getRol(int idUser) {
			
		List<String> roles = new ArrayList<>();
		mySqlConnection.open();
			
		if (!mySqlConnection.isError()) {
			ResultSet rs = mySqlConnection.executeSelect("SELECT ROLCODE_RF "
													   + "FROM db_filmcinema.rol_film "
													   		+ "INNER JOIN db_filmcinema.userrol_film ON IDROL_URF = IDROL_RF "
													   + "WHERE IDUSER_URF = " + idUser);
				
			try {
				while (rs.next()) {
					
					roles.add(rs.getString("ROLCODE_RF"));
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		mySqlConnection.close();
			
		return roles;
	}
}
