package db_access;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Admin;

public class AdminDAO implements AdminDAOIF{
	
		//sono stati implementati solo i metodi neccessari per il login
	
		private static Connection connessione;
		
		public AdminDAO(){
			connessione = DBconnection.getConnection();
		}
		
		
		//returns Admin con dati associati a username se la password è giusta
		public Admin AdminLogin(String username , String password) throws SQLException, NoSuchAlgorithmException{
			Admin a = new Admin();
			String query = "SELECT * FROM sistemavoto.utenti WHERE username = ? AND password = ? AND isAdmin = 1;";
			PreparedStatement ps = connessione.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, DBconnection.passwordhash(password));
			ResultSet r = ps.executeQuery();
			if(r.next()){
				a.setNome(r.getString("nome"));
				a.setCognome(r.getString("cognome"));
				a.setNazioneDiNascita(r.getString("nazionedinascita"));
				a.setComuneDiNascita(r.getString("comunedinascita"));
				@SuppressWarnings("deprecation")
				int[] ddn= new int[] {r.getDate("datadinascita").getDay(),r.getDate("datadinascita").getMonth() + 1,r.getDate("datadinascita").getYear()};
				a.setDataDiNascita(ddn);
				a.setCodiceFiscale(r.getString("codicefiscale").toCharArray());
				a.setSesso(r.getString("Sesso").charAt(0));
				System.out.println(a.toString());
			}
			else{ 
				System.out.println("no matching results in database");
				return null;
			}
			return a;
	}
}
