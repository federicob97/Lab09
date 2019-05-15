package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

Model model;
	
	public void setModel(Model model) {
		this.model = model;
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button btntrova;
    
    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	txtResult.clear();
		int anno = Integer.parseInt(txtAnno.getText());
		long start = System.nanoTime();
		model.creaGrafo(anno);
		long stop = System.nanoTime();
		txtResult.appendText("tempo esecuzione: "+(stop-start)+" ns\n");
		for(Country c : model.getMap().values()) {
			txtResult.appendText(c.getName()+" grado: "+c.getGrado()+" componente connessa: "+model.compConn(c)+"\n");
		}
		txtResult.appendText("numero vertici: "+model.getMap().values().size());
		comboBox.setDisable(false);
		btntrova.setDisable(false);
		
		comboBox.getItems().addAll(model.getCountries());
		
		
    }

    @FXML
    void doClear(ActionEvent event) {
    	txtResult.clear();
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	String state = comboBox.getValue();
    	int x = 0;
    	for(Country c : model.getMap().values()) {
    		if(c.getName().compareTo(state)==0) {
    			x = c.getCode();
    			break;
    		}
    	}
    	List<Country> vicini = new ArrayList<Country>(model.getVicini(model.getMap().get(x)));
    	txtResult.clear();
    	txtResult.appendText("I paesi raggiungibili da "+state+":\n");
    	for(Country p : vicini) {
    		txtResult.appendText(p.getName()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'Borders.fxml'.";
        assert btntrova != null : "fx:id=\"btntrova\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }
}

