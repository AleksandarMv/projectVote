package db_access;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import model.Admin;

public interface AdminDAOIF {
	
	public Admin AdminLogin(String username , String password) throws SQLException, NoSuchAlgorithmException;
	
}
