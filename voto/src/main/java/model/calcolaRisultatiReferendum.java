package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import db_access.DBconnection;
import db_access.ElettoreDAO;
import db_access.ElettoreDAOIF;
import db_access.ElezioneDAO;
import db_access.ElezioneDAOIF;
import db_access.VotoDAO;
import db_access.VotoDAOIF;

public class calcolaRisultatiReferendum implements calcoloRisultati{

	public HashMap<String, Float> calcolaRisultati(String idElezione)throws SQLException {
		ElezioneDAOIF edao = new ElezioneDAO(); 
		VotoDAOIF vdao = new VotoDAO();
		ElettoreDAOIF tdao= new ElettoreDAO();
		Elezione e;
		try {
			e = edao.getElezioneNonAttiva(idElezione);
		}catch (Exception exc){
			exc.printStackTrace();
			return null;
		}
		
		List<Voto> listaVoti;
		
		try{
			listaVoti = vdao.getAll(e.id);
			System.out.println(listaVoti);
		}catch(Exception exc){
			System.out.println(exc.toString());
			exc.printStackTrace();
			return null;
		}
		
		if(e.tipoElezione.contains("referendum")){
			if(e.tipoElezione.contains("con quorum")){
				int i = 0;
				
				try{
					i = tdao.countElettori();
				}
				catch(Exception exc){
					exc.printStackTrace();
					return null;
				}
				
				
				Float cfr = new Float(i);
				cfr /= 2;
				System.out.println("cfr : " + cfr);
				System.out.println("listavoti.size : " + listaVoti.size());
				System.out.println("cfr <= size   " + (cfr <= new Float(listaVoti.size())));
				if(cfr >= new Float(listaVoti.size())){
					HashMap <String, Float> results = new HashMap<String, Float>();
					results.put("quorum non raggiunto", new Float(100));
					return results;//results;
				}
				else{
					HashMap <String, Float> results = new HashMap<String, Float>();
					for (int j = 0; j < listaVoti.size(); j++){
						if(results.containsKey(listaVoti.get(j).idCandidato)){
							results.replace(listaVoti.get(j).idCandidato, results.get(listaVoti.get(j).idCandidato) + 1);
						}
						else{
							results.put(listaVoti.get(j).idCandidato, new Float(1));
						}
					}
					
					results.forEach((key, value) -> {
						float temp = (value / listaVoti.size()) * 100;
						results.put(key, temp);
					});
					
					return results;//results;
				}
				
			}
			else if(e.tipoElezione.contains("senza quorum")){
				HashMap <String, Float> results = new HashMap<String, Float>();
				for (int j = 0; j < listaVoti.size(); j++){
					if(results.containsKey(listaVoti.get(j).idCandidato)){
						results.replace(listaVoti.get(j).idCandidato, results.get(listaVoti.get(j).idCandidato) + 1);
					}
					else{
						results.put(listaVoti.get(j).idCandidato,new Float(1));
					}
				}
				
				results.forEach((key, value) -> {
					float temp = (value / listaVoti.size()) * 100;
					value = new Float(temp);
					results.put(key, temp);
				});  
				
				return results;//results;
			}
		}
		
		return null;
	}

}
