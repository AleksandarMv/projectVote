package db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Candidato;
import model.Elezione;

public class ElezioneDAO implements ElezioneDAOIF{
	
	private static Connection connessione; 
	
	public ElezioneDAO(){
		connessione = DBconnection.getConnection();
	}
	
	public boolean uploadElezione(Elezione e) throws SQLException {
		if ( null != this.getElezioneAttiva(e.id) || null != this.getElezioneNonAttiva(e.id)){
			System.out.println("esiste gia una elezione con questo id");
			return false;
		}
		String query = "INSERT INTO sistemavoto.elezioni (idElezioni , attiva, tipoElezione, conclusa) VALUES (? , ?, ?, ?);";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, e.id);
		ps.setBoolean(2, false);
		ps.setString(3, e.tipoElezione);
		ps.setInt(4, 0);
		System.out.println(ps.toString());
		ps.executeUpdate();
		
		CandidatoDAOIF CDAO = new CandidatoDAO();
		List<Candidato> candidati = e.getAllCandidati();
		
		for (int i = 0; i < candidati.size(); i++){
			CDAO.uploadCandidato(candidati.get(i).id, e.id);
		}
		return true;
	}

	public Elezione getElezioneAttiva(String id) throws SQLException {
		String query = "SELECT * FROM sistemavoto.elezioni WHERE idElezioni = ? AND attiva = 1;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, id);
		ResultSet r = ps.executeQuery();
		
		if(r.next()){
			CandidatoDAOIF candidati = new CandidatoDAO();
			return new Elezione(id ,r.getString("tipoElezione"), candidati.getAllCandidato(id), true);
		}else return null;
	}
	
	public Elezione getElezioneNonAttiva(String id) throws SQLException {
		String query = "SELECT * FROM sistemavoto.elezioni WHERE idElezioni = ? AND attiva = 0;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, id);
		ResultSet r = ps.executeQuery();
		
		if(r.next()){
			CandidatoDAOIF candidati = new CandidatoDAO();
			return new Elezione(id ,r.getString("tipoElezione"),candidati.getAllCandidato(id), false);
		}else return null;
	}

	public List<Elezione> getAllElezioneAttiva() throws SQLException {
		String query = "SELECT * FROM sistemavoto.elezioni WHERE attiva = 1 AND conclusa = 0;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ResultSet r = ps.executeQuery();
		
		List<Elezione> lista= new ArrayList<Elezione>();
		CandidatoDAOIF candidati = new CandidatoDAO();
		while(r.next()){
			Elezione temp = new Elezione(r.getString("idElezioni"),r.getString("tipoElezione"),candidati.getAllCandidato(r.getString("idElezioni")), true);
			lista.add(temp);
		}
		return lista;
	}
	public List<Elezione> getAllElezioneNonAttiva() throws SQLException {
		String query = "SELECT * FROM sistemavoto.elezioni WHERE attiva = 0 AND conclusa = 0;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ResultSet r = ps.executeQuery();
		
		List<Elezione> lista= new ArrayList<Elezione>();
		CandidatoDAOIF candidati = new CandidatoDAO();
		while(r.next()){
			Elezione temp = new Elezione(r.getString("idElezioni"),r.getString("tipoElezione"), candidati.getAllCandidato(r.getString("idElezioni")), true);
			lista.add(temp);
		}
		return lista;
	}
	
	public List<Elezione> getAllElezioneConclusa() throws SQLException{
		String query = "SELECT * FROM sistemavoto.elezioni WHERE conclusa = 1;";
		PreparedStatement ps = connessione.prepareStatement(query);
		ResultSet r = ps.executeQuery();
		
		List<Elezione> lista= new ArrayList<Elezione>();
		CandidatoDAOIF candidati = new CandidatoDAO();
		while(r.next()){
			Elezione temp = new Elezione(r.getString("idElezioni"),r.getString("tipoElezione"), candidati.getAllCandidato(r.getString("idElezioni")), true);
			lista.add(temp);
		}
		return lista;
		
	}
	
	public boolean attivaElezione(String id) throws SQLException {
		if(this.getElezioneNonAttiva(id) == null) return false;
		String query = "UPDATE sistemavoto.elezioni SET attiva = 1 WHERE idElezioni = ?";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, id);
		ps.executeUpdate();
		return true;
	}
	
	public boolean disattivaElezione(String id) throws SQLException {
		if(this.getElezioneAttiva(id) == null) return false;
		String query = "UPDATE sistemavoto.elezioni SET attiva = 0 WHERE idElezioni = ?";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, id);
		ps.executeUpdate();
		return true;
	}
	
	public boolean concludiElezione(String id) throws SQLException {
		if(this.getElezioneNonAttiva(id) == null) return false;
		String query = "UPDATE sistemavoto.elezioni SET conclusa = 1 WHERE idElezioni = ?";
		PreparedStatement ps = connessione.prepareStatement(query);
		ps.setString(1, id);
		ps.executeUpdate();
		return true;
	}
	/*
	public static void main(String args[]) throws SQLException{
		ElezioneDAOIF e = new ElezioneDAO();
		Candidato k = new Candidato("ketchup");
		Candidato m = new Candidato("maio");
		Candidato s = new Candidato("senape");
		Candidato b = new Candidato("BBQ");
		List<Candidato> salse = new ArrayList<Candidato>();
		salse.add(k); salse.add(m); salse.add(s); salse.add(b);
		e.uploadElezione(new Elezione("salsa migliore","categorico",salse));
		
		Candidato y = new Candidato("Yamaha");
		Candidato k2 = new Candidato("Kawasaki");
		Candidato h = new Candidato("Honda");
		Candidato t = new Candidato("Triumph");
		Candidato d = new Candidato("Ducati");
		Candidato s2 = new Candidato("Suzuki");
		
		List<Candidato> moto = new ArrayList<Candidato>();
		moto.add(y);moto.add(k2);moto.add(h);moto.add(t);moto.add(d);moto.add(s2);
		e.uploadElezione(new Elezione("moto migliore","categorico", moto));
		e.attivaElezione("moto migliore");
		
		
		e.uploadElezione(new Elezione("ranking moto" , "preferenziale", moto));
		e.attivaElezione("ranking moto");
		List<Candidato> r = new ArrayList<Candidato>();
		r.add(new Candidato("pro")); r.add(new Candidato("contro"));
		e.uploadElezione(new Elezione("referendum Ducati", "referendum con quorum", r));
		e.attivaElezione("referendum Ducati");
	}
	*/
}
