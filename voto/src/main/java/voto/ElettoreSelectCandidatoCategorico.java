package voto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import data_manager.DataController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import model.Candidato;
import model.Voto;

public class ElettoreSelectCandidatoCategorico extends MainController implements Initializable{
	@FXML
	private Button back;
	
	@FXML
	private Button conferma;
	
	@FXML
	private ChoiceBox<String> tuttiCandidati;
	
	@FXML
	private Button schedaBianca;
	
	public void backAction(ActionEvent event) throws IOException{
		DataController.idElezioneSelezionata = null;
		System.out.println("pressed");
		App.setRoot("loginsuccess");
	}
	
	public void confermaVoto(ActionEvent event) throws IOException, SQLException{
		Voto voto = new Voto(DataController.idElezioneSelezionata, tuttiCandidati.getValue(), 1);
		voto.registra(DataController.elettore);
		cleanDataController();
		App.setRoot("login");
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		List<Candidato> TuttiCandidati;
		try {
			TuttiCandidati = Candidato.getAllCandidato(DataController.idElezioneSelezionata);
			
		} catch (SQLException e){
			e.printStackTrace();
			return;
		}
		
		List<String> StringList  = new ArrayList<String>();
		for (int i = 0; i < TuttiCandidati.size() ; i++){
			StringList.add(TuttiCandidati.get(i).id);
		}
		System.out.println(StringList.toString());
		
		tuttiCandidati.getItems().addAll(StringList);
		
		tuttiCandidati.setValue(tuttiCandidati.getItems().get(0));
	}
	
	
	public void schedabianca(ActionEvent event) throws IOException{
		cleanDataController();
		App.setRoot("login");
	}
}
