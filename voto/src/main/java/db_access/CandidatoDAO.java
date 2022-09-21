package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import model.Candidato;

public class CandidatoDAO implements CandidatoDAOIF{
	private static Connection connessione; 

	public CandidatoDAO(){
		connessione = DBconnection.getConnection();
	}

	public boolean uploadCandidato(String id, String elezioneId) throws SQLException {
		//controlla se esiste elezione
		String query = "SELECT * FROM sistemavoto.elezioni WHERE idElezioni = ?;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, elezioneId);
		ResultSet r = ps.executeQuery();
		//controlla se esiste candidato
		if (null != this.getCandidato(id, elezioneId)) return false;
		if(r.next()){
			query = "INSERT INTO sistemavoto.candidati (idCandidati , idElezione) VALUES (?,?);";
			ps = connessione.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, elezioneId);
			ps.executeUpdate();
			return true;
		}
		return false;
	}

	public Candidato getCandidato(String id, String elezioneId) throws SQLException {
		String query = "SELECT * FROM sistemavoto.candidati WHERE idCandidati = ? AND idElezione = ?;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, id);
		ps.setString(2, elezioneId);
		ResultSet r = ps.executeQuery();
		if(r.next()){
			return new Candidato(r.getString("idCandidati"));
		}else return null;
		
	}

	public List<Candidato> getAllCandidato(String elezioneId) throws SQLException {
		String query = "SELECT * FROM sistemavoto.candidati WHERE idElezione = ?;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, elezioneId);
		ResultSet r = ps.executeQuery();
		List<Candidato> tuttiCandidati = new ArrayList<Candidato>();
		while(r.next()){
			tuttiCandidati.add(new Candidato(r.getString("idCandidati")));
		}
		return tuttiCandidati;
	}
}
