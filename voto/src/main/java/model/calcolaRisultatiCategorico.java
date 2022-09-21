package model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import db_access.ElezioneDAO;
import db_access.ElezioneDAOIF;
import db_access.VotoDAO;
import db_access.VotoDAOIF;

public class calcolaRisultatiCategorico implements calcoloRisultati{

	public HashMap<String, Float> calcolaRisultati(String idElezione) throws SQLException {
		VotoDAOIF vdao = new VotoDAO();
		List<Voto> listaVoti = vdao.getAll(idElezione);
		HashMap<String, Float> results = new HashMap<String, Float>();
		if(listaVoti == null || listaVoti.size() == 0){
			results.put("nessuno ha votato",new Float(100));
			return results;
		}
		for(int i = 0; i < listaVoti.size(); i++){
			if(results.containsKey(listaVoti.get(i).idCandidato)){
//				System.out.println(listaVoti.get(i).idCandidato + " | " + results.get(listaVoti.get(i).idCandidato));
				results.put(listaVoti.get(i).idCandidato, results.get(listaVoti.get(i).idCandidato) + 1);
			}
			else{
//				System.out.println(listaVoti.get(i).idCandidato + " || " );
				results.put(listaVoti.get(i).idCandidato, new Float(1));
//				System.out.println(results);
			}
		}
		//calcolo percentuali
		results.forEach((key, value) -> {
			float temp = (value / listaVoti.size()) * 100;
//			System.out.println(temp);
			results.put(key, temp);
		});
		
		return results;
	}
	
	/*public static void main(String[] args) throws SQLException{
//		HashMap<String, Integer> testMap = new HashMap<String,Integer>();
//		testMap.put("testKey1", 1);
//		testMap.put("testKey1", 2);
//		System.out.println(testMap.get("testKey1"));
		
		calcolaRisultatiCategorico c = new calcolaRisultatiCategorico();
		System.out.println( c.calcolaRisultati("testElezioneCategorico"));
	}
	*/
}


