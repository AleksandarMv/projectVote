package db_access;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import model.Elettore;

//sono stati implementati solo i metodi neccessari per il login
//il main serve per aggiungere/togliere utenti dal db, è provvisorio

public class ElettoreDAO implements ElettoreDAOIF{
	private static Connection connessione; 

	public ElettoreDAO(){
		connessione = DBconnection.getConnection();
	}
	
	//returns Elettore con dati associati a username se la password è giusta
	public Elettore checkLogin(String username , String password) throws SQLException,NoSuchAlgorithmException{
		Elettore e = new Elettore();
		String query = "SELECT * FROM sistemavoto.utenti WHERE username = ? AND password = ? AND isAdmin = 0;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, DBconnection.passwordhash(password));
		ResultSet r = ps.executeQuery();
		if(r.next()){
			e.setNome(r.getString("nome"));
			e.setCognome(r.getString("cognome"));
			e.setNazioneDiNascita(r.getString("nazionedinascita"));
			e.setComuneDiNascita(r.getString("comunedinascita"));
			@SuppressWarnings("deprecation")
			int[] ddn= new int[] {r.getDate("datadinascita").getDay(),r.getDate("datadinascita").getMonth() + 1,r.getDate("datadinascita").getYear()};
			e.setDataDiNascita(ddn);
			e.setCodiceFiscale(r.getString("codicefiscale").toCharArray());
//			System.out.println("cf: " + r.getString("codicefiscale") + " cfchararray : " + Arrays.toString(r.getString("codicefiscale").toCharArray()));
			e.setSesso(r.getString("Sesso").charAt(0));
			e.getha18();
			System.out.println(e.toString());
		}
		else{
			System.out.println("no matching results in database");
			return null;
		}
		return e;
	}
	
	public int countElettori() throws SQLException{
		String query = "SELECT * FROM sistemavoto.utenti WHERE isAdmin = 0";
		PreparedStatement ps = connessione.prepareStatement(query);
		ResultSet r = ps.executeQuery();
		int i = 0;
		while(r.next()){
			i++;
		}
		return i;
	}
	
	/*public static void main(String args[]) throws SQLException{
		ElettoreDAO edao = new ElettoreDAO();
		
		
		System.out.println(edao.countElettori());
	}
	*/
/*	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws NoSuchAlgorithmException, SQLException{
		int cmd = -1;
		String username;
		String password;
		String nome;
		String cognome;
		String nazionedinascita;
		String comunedinascita;
		int giornodinascita;
		int mesedinascita;
		int annodinascita;
		String sesso;
		String codicefiscale;
		int isAdmin;
		
		
		while(cmd != 0){
			System.out.println("1 per aggiungere | 2 per rimuovere | 0 per uscire");
			Scanner s = new Scanner(System.in);
			if (s.hasNextInt()){
				cmd =s.nextInt();
				if (cmd == 1){
					System.out.println("inserisci username:");
					s = new Scanner(System.in);
					username = s.nextLine();
					System.out.println("inserisci password:");
					s = new Scanner(System.in);
					password = DBconnection.passwordhash(s.nextLine());
					System.out.println("inserisci nome:");
					s = new Scanner(System.in);
					nome = s.nextLine();
					System.out.println("inserisci cognome:");
					s = new Scanner(System.in);
					cognome = s.nextLine();
					System.out.println("nazione di nascita:");
					s = new Scanner(System.in);
					nazionedinascita = s.nextLine();
					System.out.println("inserisci comune di nascita:");
					s = new Scanner(System.in);
					comunedinascita = s.nextLine();
					System.out.println("inserisci giorno di nascita:");
					s = new Scanner(System.in);
					giornodinascita = s.nextInt();
					System.out.println("inserisci giorno di mese:");
					s = new Scanner(System.in);
					mesedinascita = s.nextInt();
					System.out.println("inserisci giorno di anno:");
					s = new Scanner(System.in);
					annodinascita = s.nextInt();
					System.out.println("inserisci sesso:");
					s = new Scanner(System.in);
					sesso = s.nextLine();
					System.out.println("inserisci codicefiscale:");
					s = new Scanner(System.in);
					codicefiscale = s.nextLine();
					System.out.println("1 per admin - 0 per utente");
					s = new Scanner(System.in);
					isAdmin = s.nextInt();
					
					String insSTR = "INSERT INTO `sistemavoto`.`utenti` (`username`, `password`, `nome`, `cognome`, `dataDiNascita`, `nazionedinascita`, `comunedinascita`, `isAdmin`, `codiceFiscale`, `sesso`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement ps = connessione.prepareStatement(insSTR);
					ps.setString(1, username);
					ps.setString(2, password);
					ps.setString(3, nome);
					ps.setString(4, cognome);
					ps.setDate(5, new Date(annodinascita, mesedinascita, giornodinascita));
					ps.setString(6, nazionedinascita);
					ps.setString(7, comunedinascita);
					ps.setInt(8, isAdmin);
					ps.setString(9, codicefiscale);
					ps.setString(10, sesso);
					ps.execute();
				}
				else if(cmd == 2){
					String delStr = "DELETE FROM `sistemavoto`.`utenti` WHERE (`username` = ?);";
					String ckpsw = "SELECT password FROM sistemavoto.utenti WHERE username = ?;";
					PreparedStatement ps2 = connessione.prepareStatement(ckpsw);
					PreparedStatement ps = connessione.prepareStatement(delStr);
					System.out.println("inserisci username");
					s = new Scanner(System.in);
					username = s.nextLine();
					ps.setString(1, username);
					ps2.setString(1, username);
					System.out.println("inserisci password");
					s = new Scanner(System.in);
					password = DBconnection.passwordhash(s.nextLine());
					ResultSet r1 = ps2.executeQuery();
					r1.next();
					String realPassW = r1.getString("password");
					if (realPassW.equals(password)){
						ps.execute();
					}
					else{
						System.out.println("password o username sbagliati");
					}
				}
			}
			else{continue;}
			
		}
	}
	*/
}
