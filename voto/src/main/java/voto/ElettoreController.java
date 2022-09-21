package voto;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.Elezione;
import data_manager.DataController;

import javafx.fxml.Initializable;

public class ElettoreController extends MainController implements Initializable{
	@FXML
	private Label name;
	
	@FXML
	private Button logout;
	
	@FXML
	private Button continua;
	
	@FXML 
	private ChoiceBox<String> scegli_elezione; 
	
 	public void userLogout(ActionEvent event) throws IOException{
		DataController.elettore.logoutElettore();
		App.setRoot("login");
	}
 	
 	public void confermaSelezione() throws SQLException, IOException{
 		
 		String selezione = scegli_elezione.getValue();
 		if (selezione.equals("nessuna elezione in corso")){
 			return;
 		}
 		Elezione elezioneSelezionata = Elezione.getElezioneAttivaFromDB(selezione);
 		DataController.idElezioneSelezionata = elezioneSelezionata.id;
 		
 		if(elezioneSelezionata.tipoElezione.contains("referendum")){
 			App.setRoot("elettoreSelectCandidatoReferendum");
 		}
 		else if(elezioneSelezionata.tipoElezione.contains("categorico")){
 			App.setRoot("elettoreSelectCandidatoCategorico");
 		}
 		else if(elezioneSelezionata.tipoElezione.contains("preferenziale")){
 			App.setRoot("elettoreSelectCandidatoPreferenziale");
 		}
 	}
 	
	public void initialize(URL arg0, ResourceBundle arg1) {
		name.setText("elettore : " + DataController.elettore.getNome() + " "+ DataController.elettore.getCognome());
		
		
		List<Elezione> TutteLeElezioni;
		try {
			TutteLeElezioni = Elezione.getAllElezioneAttiva();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		List<String> StringList = new ArrayList<String>();
		for (int i = 0; i < TutteLeElezioni.size() ; i++){
			StringList.add(TutteLeElezioni.get(i).id);
		}
		System.out.println(StringList.toString());
		if(StringList.size() == 0){
			scegli_elezione.getItems().add("nessuna elezione in corso");
			scegli_elezione.setValue(scegli_elezione.getItems().get(0));
			return;
		}
		scegli_elezione.getItems().addAll(StringList);
		scegli_elezione.setValue(scegli_elezione.getItems().get(0));
	}
}
