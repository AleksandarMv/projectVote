package db_access;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import model.Elettore;

public interface ElettoreDAOIF {
	public Elettore checkLogin(String username , String password)throws SQLException,NoSuchAlgorithmException;
	public int countElettori() throws SQLException;
}

