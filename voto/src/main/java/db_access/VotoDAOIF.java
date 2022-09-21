package db_access;

import java.sql.SQLException;
import java.util.List;

import model.Elettore;
import model.Voto;

public interface VotoDAOIF {
	public boolean registra(Voto voto, Elettore elettore) throws SQLException;
	public List<Voto> getAll(String idElezione)throws SQLException;
}
