package voto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import data_manager.DataController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Elezione;
import model.calcolaRisultatiCategorico;
import model.calcolaRisultatiPreferenziale;
import model.calcolaRisultatiReferendum;
import model.calcoloRisultati;

public class visualizzaVincitoreController extends MainController implements Initializable{
	
	@FXML
	private ListView<String> gridVincitore;
	
	@FXML
	private Button back;
	
	@FXML
	private Label labelVincitore;
	
	public void backButton(ActionEvent event) throws IOException{
		cleanDataController();
		App.setRoot("scegliElezioneVincitore");
	}
	
	private HashMap<String, Float> visualizzaVoti(calcoloRisultati calc, String idelezioni){
		HashMap <String,Float>results = null;
		try {
			results = calc.calcolaRisultati(idelezioni);
			System.out.println(results);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		results.forEach((key, value) -> {
			gridVincitore.getItems().add(key + " ha il " + value + "% di voti");
		});
		
		System.out.println(results);
		return results;
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Elezione e = null;
		HashMap <String,Float>results = null;
		try {
			e = Elezione.getElezioneNonAttivaFromDB(DataController.idElezioneSelezionata);
		} catch (SQLException exc) {
			exc.printStackTrace();
			return;
		}
		if(e.tipoElezione.contains("referendum")){
			
			results = visualizzaVoti(new calcolaRisultatiReferendum(), DataController.idElezioneSelezionata);
			
			System.out.println(results);
			ArrayList<String> winnerKey = new ArrayList<String>();
			ArrayList<Float> winnerValue = new ArrayList<Float>();
			winnerKey.add((String) results.keySet().toArray()[0]);
			winnerValue.add(results.get((String)results.keySet().toArray()[0]));
			System.out.println(winnerKey + " | " + winnerValue);
			results.forEach((key, value) -> {
				if(value > winnerValue.get(0)){
					winnerValue.set(0, value);
					winnerKey.set(0, key);
				}
				
			});
			labelVincitore.setText(winnerKey.get(0) + " ha vinto con il " + winnerValue.get(0) + "%");
			return;
		}
		else if(e.tipoElezione.contains("preferenziale")){
			
			results = visualizzaVoti(new calcolaRisultatiPreferenziale(), DataController.idElezioneSelezionata);
			
			System.out.println(results);
			ArrayList<String> winnerKey = new ArrayList<String>();
			ArrayList<Float> winnerValue = new ArrayList<Float>();
			winnerKey.add((String) results.keySet().toArray()[0]);
			winnerValue.add(results.get((String)results.keySet().toArray()[0]));
			System.out.println(winnerKey + " | " + winnerValue);
			results.forEach((key, value) -> {
				if(value > winnerValue.get(0)){
					winnerValue.set(0, value);
					winnerKey.set(0, key);
				}
				
			});
			if(e.tipoElezione.contains("maggioranza assoluta")){
				if(winnerValue.get(0)<= 50){
					labelVincitore.setText("maggioranza assoluta non raggiunta");
				}
			}
			else{
				labelVincitore.setText(winnerKey.get(0) + " ha vinto con il " + winnerValue.get(0) + "%");
				
			}
			return;
		}
		else if(e.tipoElezione.contains("categorico con preferenze")){
			
			
			return;
		}else if(e.tipoElezione.contains("categorico")){
			
			results = visualizzaVoti(new calcolaRisultatiCategorico(), DataController.idElezioneSelezionata);
			
			System.out.println(results);
			ArrayList<String> winnerKey = new ArrayList<String>();
			ArrayList<Float> winnerValue = new ArrayList<Float>();
			winnerKey.add((String) results.keySet().toArray()[0]);
			winnerValue.add(results.get((String)results.keySet().toArray()[0]));
			System.out.println(winnerKey + " | " + winnerValue);
			results.forEach((key, value) -> {
				if(value > winnerValue.get(0)){
					winnerValue.set(0, value);
					winnerKey.set(0, key);
				}
				
			});
			if(e.tipoElezione.contains("maggioranza assoluta")){
				if(winnerValue.get(0)<= 50){
					labelVincitore.setText("maggioranza assoluta non raggiunta");
				}
			}
			else{
				labelVincitore.setText(winnerKey.get(0) + " ha vinto con il " + winnerValue.get(0) + "%");
				
			}
			return;
			
		}
		
	}
	
}
