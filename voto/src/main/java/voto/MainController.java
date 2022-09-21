package voto;

import data_manager.DataController;

public abstract class MainController {
	
	protected void cleanDataController(){
		DataController.admin = null;
		DataController.elettore = null;
		DataController.idElezioneSelezionata = null;
	}
}
