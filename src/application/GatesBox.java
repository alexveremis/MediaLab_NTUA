package application;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class GatesBox {
	public static void display(ArrayList<Plane> aeroplana,AirportState aerodromio,int ActivePlanes) {
		int gateNum=0;
		Stage window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Gates");
		window.setMinWidth(1000);
		window.setMinHeight(700);
		TextArea tx=new TextArea();
		tx.setMinHeight(650);
		tx.setMinWidth(900);
		tx.clear();
		tx.appendText("The Gates of this airport are the following:\n");
		for(int i=1;i<8;i++) {
			if(aerodromio.getKatastasi(i)==1) {
				gateNum++;
				tx.appendText("Gate "+gateNum+" : "+aerodromio.getSlotID(i)+", ");
				if(aerodromio.getEmptySlots(i)==aerodromio.getWholeSlots(i))tx.appendText("TOTALLY EMPTY.\n");
				else if(aerodromio.getEmptySlots(i)==0)tx.appendText("FULL");	
				else tx.appendText("NOT EMPTY");
				if(aerodromio.getEmptySlots(i)!=aerodromio.getWholeSlots(i)) {
					tx.appendText(" and hosts the following planes:\n");
					for(int j=0;j<ActivePlanes;j++) {
						Plane aeroplanaki=aeroplana.get(j);
						if(aeroplanaki.getKatastasi()>0) {
						if(aeroplanaki.getCategoryParked()==i)	{
						tx.appendText(aeroplanaki.getFID()+", ");
						if(aeroplanaki.getKatastasi()==1)tx.appendText((aeroplanaki.getLandingTime()+aeroplanaki.getLiM())+"\n");
						else tx.appendText((aeroplanaki.getFinalLiM())+"\n");
						}
					}
				}	
			}
			}
		}
		tx.setEditable(false);
		VBox layout =new VBox(1000);
		layout.getChildren().addAll(tx);
		layout.setAlignment(Pos.CENTER);
		Scene scene =new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
