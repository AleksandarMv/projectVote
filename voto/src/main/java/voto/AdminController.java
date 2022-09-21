package voto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Candidato;
import model.Elezione;
import data_manager.DataController;
import javafx.fxml.Initializable;

public class AdminController extends MainController implements Initializable{
	
	@FXML
	private Label welcomeAdmin;
	
	@FXML
	private Button adminlogout;
	
	@FXML
	private Button caricaElezione;
	
	@FXML
	private Button aggiungiCandidato;
	
	@FXML
	private Button attivaElezione;
	
	@FXML
	private Button fermaElezione;
	
	@FXML
	private Button concludi;
	
	@FXML
	private ChoiceBox<String> elezioniAttive;
	
	@FXML
	private ChoiceBox<String> elezioniNonAttive;
	
	@FXML
	private ChoiceBox<String> tipoElezione;
	
	@FXML
	private ChoiceBox<String> modalitaVittoria;
	
	@FXML
	private TextField candidatoField;
	
	@FXML
	private TextField idElezione;
	
	@FXML
	private ListView<String> candidati;
	
	@FXML
	private Label errorMessage;
	
	private List<String> listaCandidati;

	
 	public void initialize(URL location, ResourceBundle resources){
 		welcomeAdmin.setText("admin : " + DataController.admin.getNome() + " "+ DataController.admin.getCognome());
 		
 		elezioniListUpdate();
 		
 		tipoElezione.getItems().add("categorico ");
 		//i candidati nel categorico vanno indicati nel formato (partito)|(candidato), per ogni partito diverso deve esistere
 		//un candidato nel formato partito |
 		//il categorico con preferenze viene gestito diversamente dal categorico solo nel conteggio dei voti
 		tipoElezione.getItems().add("categorico con preferenze");
 		tipoElezione.getItems().add("preferenziale ");
 		tipoElezione.getItems().add("referendum ");
 		tipoElezione.setValue(tipoElezione.getItems().get(0)); 
 		modalitaVittoria.getItems().add("maggioranza | non assoluta");
 		modalitaVittoria.getItems().add("maggioranza | assoluta");
 		modalitaVittoria.getItems().add("con quorum");
 		modalitaVittoria.getItems().add("senza quorum");
 		modalitaVittoria.setValue(modalitaVittoria.getItems().get(0));
 		
 	}
 	 
 	private void elezioniListUpdate(){
 		elezioniAttive.getItems().removeAll(elezioniAttive.getItems());
 		elezioniNonAttive.getItems().removeAll(elezioniNonAttive.getItems());
 		List<Elezione> listElezioneAttiva;
 		List<Elezione> listElezioneNonAttiva;
 		listaCandidati = new ArrayList<String>();
 		
 		try {
			listElezioneAttiva = Elezione.getAllElezioneAttiva();
			listElezioneNonAttiva = Elezione.getAllElezioneNonAttiva();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
 		
 		if(listElezioneAttiva.size() != 0){
 			for(int i = 0; i < listElezioneAttiva.size(); i++){
 	 			elezioniAttive.getItems().add(listElezioneAttiva.get(i).id);
 	 		}
 			elezioniAttive.setValue(elezioniAttive.getItems().get(0));
 		}
 		if(listElezioneNonAttiva.size() != 0){ 
 			for(int i = 0; i < listElezioneNonAttiva.size(); i++){
 	 			elezioniNonAttive.getItems().add(listElezioneNonAttiva.get(i).id);
 	 		}
 		
 	 		elezioniNonAttive.setValue(elezioniNonAttive.getItems().get(0));
 	 		
 		}
 	}
 	
 	public void adminLogout(ActionEvent event) throws IOException{
 		System.out.println(DataController.admin.toString());
 		DataController.admin.logoutAdmin();
 		cleanDataController();
 		App.setRoot("login");
 	}
 	
 	public void uploadElezione(ActionEvent event){
 		List<Candidato> listaC = new ArrayList<Candidato>();
 		
 		if (idElezione.getText().equals("") || idElezione.getText() == null){
 			errorMessage.setText("devi dare un nome all'elezione");
 			return;
 		}
 		if(listaCandidati.size() == 0){
 			errorMessage.setText("non puoi caricare un elezione senza candidati");
 			return;
 		}
 		for(int i = 0; i < listaCandidati.size(); i++ ){
 			listaC.add(new Candidato(listaCandidati.get(i)));
 		}
 		
 		if ((tipoElezione.getValue().contains("referendum") && !(modalitaVittoria.getValue().contains("quorum")))){
 			errorMessage.setText("un referendum puo avere modalita di vittoria solo con o senza quorum");
 			return;
 		}
 		
 		
 		if(idElezione.getText() != null || !idElezione.getText().equals("") || !idElezione.getText().contains(";")){
 			try {
 				System.out.println("sto cercando di caricare : " + idElezione.getText() + " || " + tipoElezione.getValue() + " " + modalitaVittoria.getValue() + listaC);
				Elezione.addElezioneToDB(new Elezione(idElezione.getText(), tipoElezione.getValue() + " " + modalitaVittoria.getValue(), listaC), DataController.admin);
				errorMessage.setText("elezione caricata");
			} catch (NullPointerException e) {
				e.printStackTrace();
				return;
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
 		}
 		
 		elezioniListUpdate();
 		
 	}
 	
 	public void aggiungiCandidato(ActionEvent event){
 		if(!tipoElezione.getValue().contains("referendum")){
 			listaCandidati.remove("pro");
 			listaCandidati.remove("contro");
 			listaCandidati.add(candidatoField.getText());
 			candidati.getItems().add(candidatoField.getText());
 		}
 		else{
 			listaCandidati.removeAll(listaCandidati);
 			candidati.getItems().removeAll(candidati.getItems());
 			listaCandidati.add("pro");
 			listaCandidati.add("contro");
 			candidati.getItems().add("pro");
 			candidati.getItems().add("contro");
 		}
 	}
 	
 	public void fermaElezione(ActionEvent event) throws SQLException{
 		
 		Elezione.stopElezione(elezioniAttive.getValue(),DataController.admin);
 		elezioniListUpdate();
 		
 	}
 	
 	public void attivaElezione(ActionEvent event) throws SQLException{
 		
 		Elezione.startElezione(elezioniNonAttive.getValue(), DataController.admin);
 		elezioniListUpdate();
 	}
 	
 	public void concludiElezione(ActionEvent event) throws SQLException{
 		
 		Elezione.concludiElezione(elezioniNonAttive.getValue(), DataController.admin);
 		elezioniListUpdate();
 	}
}
