package model;

import java.sql.SQLException;

import db_access.VotoDAO;
import db_access.VotoDAOIF;

public class Voto {
	public final String idElezione;
	public final String idCandidato;
	public final int pesoVoto;
	
	public Voto (String idEz, String idCa, int peso) throws NullPointerException{
		if (idEz == null || idCa == null) throw new NullPointerException();
		idElezione = idEz;
		idCandidato = idCa;
		pesoVoto = peso;
	}
	
	public boolean registra(Elettore elettore) throws SQLException{
		VotoDAOIF vdao = new VotoDAO();
		return vdao.registra(this, elettore);
	}
	
	
}
