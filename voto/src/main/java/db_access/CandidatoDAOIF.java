package db_access;

import model.Candidato;

import java.sql.SQLException;
import java.util.List;

public interface CandidatoDAOIF {
	
	//carica un cadidato sul db
	public boolean uploadCandidato(String id, String elezioneId) throws SQLException;
	
	//returns : un candidato con id == id e appartiene alla elezione con id == elezioneId
	public Candidato getCandidato(String id, String elezioneId) throws SQLException;
	
	//returns : tutti i candidati che appartengono alla elezione con id == elezioneId
	public List<Candidato> getAllCandidato(String elezioneId) throws SQLException;
}
