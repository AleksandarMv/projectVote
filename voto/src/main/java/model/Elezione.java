package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.ElezioneDAO;
import db_access.ElezioneDAOIF;

public class Elezione {
	
	public final String id;
	public final String tipoElezione;
	private boolean attiva;
	private List<Candidato> listacandidati;

	
	//listaC non contiene null o eId == null throws nullpointerexception
	public Elezione(String eId,String tipo, List<Candidato> listaC) throws NullPointerException{
		if (eId == null) throw new NullPointerException();
		id = eId;
		listacandidati = listaC;
		attiva = false;

		tipoElezione = tipo;
	
	}
	
	public Elezione(String eId,String tipo, List<Candidato> listaC , boolean act) throws NullPointerException{
		if (eId == null) throw new NullPointerException();
		id = eId;
		listacandidati = listaC;
		attiva = act;
		tipoElezione = tipo;
	}
	
	public List<Candidato> getAllCandidati(){
		return new ArrayList<Candidato>(listacandidati);
	}
	
	public static boolean addElezioneToDB(Elezione e, Admin a) throws SQLException{
		if(e.id != null && e.listacandidati != null && a.getAutenticato()){
			
			ElezioneDAOIF dbelezione = new ElezioneDAO();
			dbelezione.uploadElezione(e);
			
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static boolean startElezione(String eId, Admin a) throws SQLException{
		if(eId != null && a.getAutenticato()){
			
			ElezioneDAOIF dbelezione = new ElezioneDAO();
			dbelezione.attivaElezione(eId);
			
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean stopElezione(String eId, Admin a) throws SQLException{
		if(eId != null && a.getAutenticato()){
			
			ElezioneDAOIF dbelezione = new ElezioneDAO();
			dbelezione.disattivaElezione(eId);
			
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean concludiElezione(String eId, Admin a) throws SQLException{
	if(eId != null && a.getAutenticato()){
			
			ElezioneDAOIF dbelezione = new ElezioneDAO();
			dbelezione.concludiElezione(eId);
			
			return true;
		}
		else{
			return false;
		}
	}
	
	
 	public static Elezione getElezioneAttivaFromDB(String dbid) throws SQLException{
		
		ElezioneDAOIF dbelezione = new ElezioneDAO();
		return dbelezione.getElezioneAttiva(dbid);
	
	}
	
	public static Elezione getElezioneNonAttivaFromDB(String dbid) throws SQLException{
		
		ElezioneDAOIF dbelezione = new ElezioneDAO();
		return dbelezione.getElezioneNonAttiva(dbid);
	
	}
	
	public static List<Elezione> getAllElezioneAttiva() throws SQLException{
		ElezioneDAOIF dbelezione = new ElezioneDAO();
		return dbelezione.getAllElezioneAttiva();
		
	}
	
	public static List<Elezione> getAllElezioneNonAttiva() throws SQLException{
		ElezioneDAOIF dbelezione = new ElezioneDAO();
		return dbelezione.getAllElezioneNonAttiva();
		
	}
	
	public static List<Elezione> getAllElezioneConclusa() throws SQLException{
		ElezioneDAOIF dbelezione = new ElezioneDAO();
		return dbelezione.getAllElezioneConclusa();
		
	}
	
	
}
