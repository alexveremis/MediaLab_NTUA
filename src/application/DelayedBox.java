package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Delayed;

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

public class DelayedBox {
	public static void display(ArrayList<Plane> aeroplana,AirportState aerodromio,int ActivePlanes,int Delayeded) {
		Stage window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Delayed Flights");
		window.setMinWidth(1000);
		window.setMinHeight(700);
		TextArea tx=new TextArea();
		tx.setMinHeight(650);
		tx.setMinWidth(900);
		tx.clear();
		if(Delayeded>0) {
		tx.appendText("The Flights that have a delay, are "+ Delayeded+" and have the following characteristics:\n");
		for(int j=0;j<ActivePlanes;j++) {
			Plane aeroplanaki=aeroplana.get(j);
			if(aeroplanaki.getKatastasi()==2) {
				if(aeroplanaki.getFinalLiM()>aeroplanaki.getLiM()) {
					tx.appendText(aerodromio.getSlotID(aeroplanaki.getCategoryParked()));
					tx.appendText(" ,"+aeroplanaki.getFID());
					if(aeroplanaki.getToF()==1)tx.appendText(", Epivatiki");
					else if(aeroplanaki.getToF()==2)tx.appendText(", Emporeumatiki");
					else tx.appendText(", Idiwtiki");
					if(aeroplanaki.getToP()==1)tx.appendText(", Mono, ");
					else if(aeroplanaki.getToP()==2)tx.appendText(", Turbo, ");
					else tx.appendText(", Jet, ");
					tx.appendText(aeroplanaki.getFinalLiM() +".\n");
				}
			}	
		}
		}
		else tx.appendText("No Plane has delay");
		tx.setEditable(false);
		VBox layout =new VBox(1000);
		layout.getChildren().addAll(tx);
		layout.setAlignment(Pos.CENTER);
		Scene scene =new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
