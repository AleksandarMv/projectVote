package db_access;

import model.Elezione;

import java.sql.SQLException;
import java.util.List;

public interface ElezioneDAOIF {
	public boolean uploadElezione(Elezione e) throws SQLException;
	public Elezione getElezioneAttiva(String id) throws SQLException;
	public Elezione getElezioneNonAttiva(String id) throws SQLException;
	public List<Elezione> getAllElezioneAttiva() throws SQLException;
	public List<Elezione> getAllElezioneNonAttiva() throws SQLException;
	public List<Elezione> getAllElezioneConclusa() throws SQLException;
	public boolean attivaElezione(String id) throws SQLException;
	public boolean disattivaElezione(String id) throws SQLException;
	public boolean concludiElezione(String id) throws SQLException;
}
