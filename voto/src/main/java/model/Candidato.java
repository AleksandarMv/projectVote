package model;

import java.sql.SQLException;
import java.util.List;

import db_access.CandidatoDAO;
import db_access.CandidatoDAOIF;

public class Candidato{
	public final String id;
	
	public Candidato(String nome){
		id = nome;
	}
	@Override
	public String toString(){
		return id;
	}
	@Override
	public boolean equals(Object o){
		if (o instanceof Candidato){
			Candidato c = (Candidato) o;
			return (this.id.equals(c.id));
		}
		else return false;
	}
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	public static List<Candidato> getAllCandidato(String eId) throws SQLException{
		CandidatoDAOIF edao = new CandidatoDAO();
		return edao.getAllCandidato(eId);
	}
}
