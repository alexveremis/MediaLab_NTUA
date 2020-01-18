package application;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.webkit.ThemeClient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NextDeparturesBox{
	public static void display(ArrayList<Plane> aeroplana,AirportState aerodromio,int ActivePlanes,int FLi10) {
		Stage window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Next Departures");
		window.setMinWidth(1000);
		window.setMinHeight(700);
		TextArea tx=new TextArea();
		tx.setMinHeight(650);
		tx.setMinWidth(900);
		tx.clear();
		if(FLi10>0) {
		tx.appendText("The Flights leaving in the next 10 mins are "+ FLi10+" and have the following characteristics:\n");
		for(int j=0;j<ActivePlanes;j++) {
			Plane aeroplanaki=aeroplana.get(j);
			if(aeroplanaki.getKatastasi()==2) {
				if(aeroplanaki.getFinalLiM()<=10) {
					tx.appendText(aeroplanaki.getFID());
					if(aeroplanaki.getToF()==1)tx.appendText(", Epivatiki");
					else if(aeroplanaki.getToF()==2)tx.appendText(", Emporeumatiki");
					else tx.appendText(", Idiwtiki");
					if(aeroplanaki.getToP()==1)tx.appendText(", Mono\n");
					else if(aeroplanaki.getToP()==2)tx.appendText(", Turbo\n");
					else tx.appendText(", Jet\n");
				}
			}	
		}
		}
		else tx.appendText("No Plane is Leaving in the next 10 mins");
		VBox layout =new VBox(1000);
		layout.getChildren().addAll(tx);
		layout.setAlignment(Pos.CENTER);
		Scene scene =new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
