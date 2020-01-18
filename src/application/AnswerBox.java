package application;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AnswerBox {
	public static void display(String title ,String message) {
		Stage window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(500);
		Label label=new Label();
		label.setText(message);
		String st2 = "-fx-font-weight: bold";
		label.setStyle(st2);
		label.setTextFill(Color.WHITE);
		Button closeButton =new Button("Close pop up window");
		closeButton.setOnAction(e->window.close());
		VBox layout =new VBox(10);
		String style = "-fx-background-color: rgba(108, 162, 37, 0.92);";
		layout.setStyle(style);
		layout.getChildren().addAll(label,closeButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene =new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
