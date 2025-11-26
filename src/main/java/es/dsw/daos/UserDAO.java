package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.User;

public class UserDAO {
	
	private MySqlConnection mySqlConnection;

	public UserDAO() {
		mySqlConnection = new MySqlConnection();
	}
	
	public List<User> getAll() {
		
		List<User> listUsers = new ArrayList<User>();
		mySqlConnection.open();
		
		if (!mySqlConnection.isError()) {
			ResultSet rs = mySqlConnection.executeSelect("SELECT * FROM db_filmcinema.user_film");
			
			try {
				while (rs.next()) {
					
					User user = new User();
					
					user.setIdUser(rs.getInt("IDUSER_USF"));
					user.setUserName(rs.getString("USERNAME_USF"));
					user.setPassword(rs.getString("PASSWORD_USF"));
					user.setName(rs.getString("NAME_USF"));
					user.setFirstSurname(rs.getString("FIRSTSURNAME_USF"));
					user.setEmail(rs.getString("EMAIL_USF"));
					
					listUsers.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mySqlConnection.close();
		
		return listUsers;
	}
	
	public User getUserByUsername(String username) {
	    User user = null;
	    mySqlConnection.open();

	    if (!mySqlConnection.isError()) {
	    	
	        ResultSet rs = mySqlConnection.executeSelect("SELECT * FROM db_filmcinema.user_film WHERE USERNAME_USF = '" + username + "'");

	        try {
	            if (rs.next()) {
	            	
	                user = new User();
	                
	                user.setIdUser(rs.getInt("IDUSER_USF"));
	                user.setUserName(rs.getString("USERNAME_USF"));
	                user.setPassword(rs.getString("PASSWORD_USF"));
	                user.setName(rs.getString("NAME_USF"));
	                user.setFirstSurname(rs.getString("FIRSTSURNAME_USF"));
	                user.setEmail(rs.getString("EMAIL_USF"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    mySqlConnection.close();
	    return user;
	}
}
