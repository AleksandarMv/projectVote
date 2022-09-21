package model;

import java.sql.SQLException;
import java.util.HashMap;

public interface calcoloRisultati {
	HashMap <String, Float> calcolaRisultati(String idElezione) throws SQLException;
}
