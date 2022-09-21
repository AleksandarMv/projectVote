package voto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import data_manager.DataController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import model.Elezione;

public class scegliElezioneVincitoreController extends MainController implements Initializable{
	@FXML
	private ChoiceBox<String> elezioniNonAttive;
	
	@FXML
	private Button continua;
	
	@FXML
	private Button indietro;
	
	public void continuaButton(ActionEvent event) throws IOException{
		if(elezioniNonAttive.getItems().size() != 0){
			DataController.idElezioneSelezionata = elezioniNonAttive.getValue();
			App.setRoot("visualizzaVincitore");
		}
	}
	
	public void backAction(ActionEvent event) throws IOException{
		App.setRoot("login");
		cleanDataController();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Elezione> listaElezioni;
		try {
			listaElezioni = Elezione.getAllElezioneConclusa();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		for (int i = 0; i < listaElezioni.size(); i++){
			elezioniNonAttive.getItems().add(listaElezioni.get(i).id);
		}
		if(elezioniNonAttive.getItems().size() != 0){
			elezioniNonAttive.setValue(elezioniNonAttive.getItems().get(0));
		}
		
	}

}
