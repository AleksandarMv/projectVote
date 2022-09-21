package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Elettore;
import model.Voto;

public class VotoDAO implements VotoDAOIF{
	private static Connection connessione; 
	
	public VotoDAO(){
		connessione = DBconnection.getConnection();
	}
	
	public boolean registra(Voto voto, Elettore elettore) throws SQLException {
		if(checkDoppioVoto(voto, elettore) == true){
			return false;
		}
		String query = "INSERT INTO sistemavoto.voti (idElezione , idElettore, peso, idCandidato) VALUES (?, ?, ?, ?);";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, voto.idElezione);
		ps.setInt(3, voto.pesoVoto);
		ps.setString(4, voto.idCandidato);
		try{
			ps.setString(2, DBconnection.passwordhash(Arrays.toString((elettore.getCodiceFiscale()))));
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		ps.executeUpdate();
		return true;
	}
	
	private boolean checkDoppioVoto(Voto voto, Elettore elettore) throws SQLException{
		String query = "SELECT * FROM sistemavoto.voti WHERE idElezione = ? AND idElettore = ? AND peso = ?;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, voto.idElezione);
		ps.setInt(3, voto.pesoVoto);
		try{
			System.out.println(DBconnection.passwordhash(Arrays.toString(elettore.getCodiceFiscale())) + "|| cf : " + Arrays.toString(elettore.getCodiceFiscale()));
			ps.setString(2, DBconnection.passwordhash(Arrays.toString((elettore.getCodiceFiscale()))));
		}catch (Exception e){
			e.printStackTrace();
			return true;
		}
		System.out.println(ps.toString());
		ResultSet r = ps.executeQuery();
		boolean result = r.next();
		System.out.println("questo voto è stato registrato ? : " + result);
		return result;
	}

	public List<Voto> getAll(String idElezione) throws SQLException {
		String query = "SELECT * FROM sistemavoto.voti WHERE idElezione = ?;";
		PreparedStatement ps;
		ps = connessione.prepareStatement(query);
		ps.setString(1, idElezione);
		ResultSet r = ps.executeQuery();
		List <Voto> resultList = new ArrayList<Voto>();
		while(r.next()){
			Voto temp = new Voto(r.getString("idElezione"), r.getString("idCandidato"), r.getInt("peso"));
			resultList.add(temp);
		}
		return resultList;
	}

}
