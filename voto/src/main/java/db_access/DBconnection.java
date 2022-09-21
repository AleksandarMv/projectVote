package db_access;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;

public final class DBconnection {
	
	private static DBconnection instance;
	private Connection myDbConnection;
	
	private DBconnection(){
		instance = this;
		myDbConnection = dbconnection();
	}
	
	private static Connection dbconnection(){
		Connection c = null;
		String dbUser = "root";
		String dbPass = "aleX/123";
		String connectionString = "jdbc:mysql://localhost:42000/sistemavoto?serverTimezone=UTC";
		 try {
			 c = DriverManager.getConnection(connectionString, dbUser, dbPass);
			 System.out.println("connesso al db");
			 return c;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 System.out.println("no connection");
		 }
		 return c;
	}
	
	//restituisce connessione al mio database
	public static Connection getConnection(){
		if (instance == null) instance = new DBconnection();
		return instance.myDbConnection;
	}
	
	//requires  password not null
	//restituisce stringa di byte interpretati in base64 del risultato della funziona sha-256 sulla stringa password 	
	public static String passwordhash(String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] bytes = md.digest(password.getBytes());
		//for (int i = 0; i<bytes.length; i++){
		//}
		return Base64.getEncoder().withoutPadding().encodeToString(bytes).toString();
	}
	
	 //usato per generare i codici hash da inserire nel db
/*	public static void main(String args[]) throws NoSuchAlgorithmException{
		System.out.println("test1 : " + passwordhash("test1") + ";"+passwordhash("test1").length());
		System.out.println("test2 : " + passwordhash("test2") + ";"+passwordhash("test2").length());
		System.out.println("test3 : " + passwordhash("test3") + ";"+passwordhash("test3").length());
		System.out.println("test4 : " + passwordhash("test4") + ";"+passwordhash("test4").length());
		System.out.println("test5 : " + passwordhash("test5") + ";"+passwordhash("test5").length());
		System.out.println("alex123: "+ passwordhash("alex123") + ";"+passwordhash("alex123").length());
		System.out.println("alex456: "+ passwordhash("alex456") + ";"+passwordhash("alex456").length());
	}
*/
}