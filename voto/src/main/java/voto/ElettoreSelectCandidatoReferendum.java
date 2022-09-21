package voto;

import java.io.IOException;
import java.sql.SQLException;

import data_manager.DataController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Voto;

public class ElettoreSelectCandidatoReferendum extends MainController{
	
	@FXML
	private Button back;
	@FXML
	private Button pro;
	@FXML
	private Button contro;
	@FXML
	private Button schedaBianca;
	
	public void backAction(ActionEvent event) throws IOException{
		DataController.idElezioneSelezionata = null;
		System.out.println("pressed");
		App.setRoot("loginsuccess");
	}
	
	public void confermaPro(ActionEvent event) throws IOException, SQLException{
		Voto voto = new Voto(DataController.idElezioneSelezionata,"pro", 1);
		voto.registra(DataController.elettore);
		cleanDataController();
		App.setRoot("login");
	}
	public void confermaContro(ActionEvent event) throws IOException, SQLException{
		Voto voto = new Voto(DataController.idElezioneSelezionata,"contro", 1);
		voto.registra(DataController.elettore);
		cleanDataController();
		App.setRoot("login");
	}
	public void schedabianca(ActionEvent event) throws IOException{
		cleanDataController();
		App.setRoot("login");
	}

}
