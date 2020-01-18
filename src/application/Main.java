package application;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList; 
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.javafx.scene.CameraHelper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable; 
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class Main extends Application{
	Timeline time;
	ObservableList<String> ToF_List= FXCollections.observableArrayList("Epivatiki","Emporeumatiki","Idiwtiki");
	ObservableList<String> ToP_List= FXCollections.observableArrayList("Mono","Turbo","Jet");
	@FXML  
	private ChoiceBox<String> TypeofFlight;
	@FXML 
	private ChoiceBox<String> TypeofPlane; 
	@FXML
	private CheckBox Gas;
	@FXML
	private CheckBox Cleaning;
	@FXML
	private CheckBox Passengers;
	@FXML
	private CheckBox UnLoad;
	@FXML 
	private TextField FIDTextField;
	@FXML 
	private TextField LiMTextField;
	@FXML 
	private TextField DestTextField;
	@FXML
	private TextArea GenikosXwrosStathmeusis;
	@FXML
	private TextArea MD;
	@FXML
	private TextArea Pylh;
	@FXML
	private TextArea EmporikhPylh;
	@FXML
	private TextArea ZoneA;
	@FXML
	private TextArea ZoneB;
	@FXML
	private TextArea ZoneC;
	@FXML
	private ScrollPane GenikosXwrosStathmeusisSP;
	@FXML
	private ScrollPane MDSP;
	@FXML
	private ScrollPane PylhSP;
	@FXML
	private ScrollPane EmporikhPylhSP;
	@FXML
	private ScrollPane ZoneASP;
	@FXML
	private ScrollPane ZoneBSP;
	@FXML
	private ScrollPane ZoneCSP=new ScrollPane();
	@FXML 
	private TextArea TotalTimeTA;
	@FXML 
	private TextArea TotalFlightsArrivingTA;
	@FXML 
	private TextArea TotalMoneyTA;
	@FXML 
	private TextArea Leavingin10TA;
	@FXML 
	private TextArea TotalNumberESTA;
	@FXML 
	private TextArea MessageComponent;
	AirportState aerodromio = new Airport();
	private static ArrayList<Plane> aeroplana = new ArrayList<Plane>(); 
	private static int ActivePlanes,totalFlightsArrived,FlightsLeavingin10mins,Mins,Hours,NumofDelFlights;
	private static double totalCost;
	private static String SCENARIO_IDargument;
	public void main2(String args) {
		aeroplana.clear();
		String SCENARIO_ID=args;
		String[] tempo;
		String line,FID,C,SID;
		int Cat,ES,Cost,i=0,ToF,ToP,LiM,Yp;
		ActivePlanes=totalFlightsArrived=Mins=Hours=0;
		totalCost=0.0;
		for (i=0;i<8;i++)aerodromio.setAll(i+1,0,0,"0",i,0);
		i=0;
			try {
				BufferedReader reader = new BufferedReader(new FileReader("multimedia/airport_"+SCENARIO_ID+".txt"));
				while((line=reader.readLine())!=null) {
					tempo=line.split(",");
					Cat=Integer.parseInt(tempo[0]);
					ES=Integer.parseInt(tempo[1]);
					Cost=Integer.parseInt(tempo[2]);
					SID=tempo[3];
					aerodromio.setAll(Cat,ES,Cost,SID,i,1);
					i++;
				}
				reader.close();
				aerodromio.setN(i);
				reader = new BufferedReader(new FileReader("multimedia/setup_"+SCENARIO_ID+".txt"));
				i=0;
				while((line=reader.readLine())!=null) {
					tempo=line.split(",");
					FID=tempo[0];
					C=tempo[1];
					ToF=Integer.parseInt(tempo[2]);
					ToP=Integer.parseInt(tempo[3]);
					LiM=Integer.parseInt(tempo[4]);
					Yp=Integer.parseInt(tempo[5]);
					Plane aeroplanaki =new Plane();
					aeroplanaki.setAll(FID,C,ToF,ToP,LiM,i,Yp);
					aeroplana.add(aeroplanaki);
					totalFlightsArrived++;
					ActivePlanes++;
				}
				reader.close();
				//i=readPlanes(args[1],i);
				System.out.println(ActivePlanes);
				findPlaceforPlanes(i,aerodromio);
				//System.out.println((end-start)/5000);//ena lepto==5000msec edw
			
			}
			catch(IOException e) { //NotFoundException
				System.err.format("There are not such 'airport_%s.txt' and 'setup_%s.txt' ",SCENARIO_ID,SCENARIO_ID);
				e.printStackTrace(); 
			}
			
			if(aerodromio.getKatastasi(1)==0)PylhSP.setVisible(false);
			if(aerodromio.getKatastasi(2)==0)EmporikhPylhSP.setVisible(false);
			if(aerodromio.getKatastasi(3)==0)ZoneASP.setVisible(false);
			if(aerodromio.getKatastasi(4)==0)ZoneBSP.setVisible(false); 
			if(aerodromio.getKatastasi(5)==0) {ZoneCSP.setVisible(false);}
			if(aerodromio.getKatastasi(6)==0)GenikosXwrosStathmeusisSP.setVisible(false);
			if(aerodromio.getKatastasi(7)==0)MDSP.setVisible(false);

			
	}
	public static int readPlanes(String ARG,int i) {
			String[] tempo;
			String FID,C;
			int ToF,ToP,LiM,Yp;
			tempo=ARG.split(",");
			FID=tempo[0];
			C=tempo[1];
			ToF=Integer.parseInt(tempo[2]);
			ToP=Integer.parseInt(tempo[3]);
			LiM=Integer.parseInt(tempo[4]);
			Yp=Integer.parseInt(tempo[5]);
			Plane aeroplanaki=new Plane();
			aeroplanaki.setAll(FID,C,ToF,ToP,LiM,i,Yp);
			aeroplana.add(aeroplanaki);
			ActivePlanes++;
			totalFlightsArrived++;
			return i;
		}
	private static void findPlaceforPlanes(int i,AirportState aerodromio) {
		//leipei if katastasi!=1
		int j,k,Category;
		String FID;
		for(j=0;j<ActivePlanes;j++) {
			Plane aeroplanaki =aeroplana.get(j);
			if(aeroplanaki.getHrsFC()==-1) {
				aeroplanaki.setHrsFC(Hours);
				aeroplanaki.setMinsFC(Mins);
			}
			if(aeroplanaki.getKatastasi()==0) {
				Category=findBestCategory(aeroplanaki.getToF(),aeroplanaki.getToP(),aeroplanaki.getLiM(),aerodromio,aeroplanaki.getYphresies());
				if(Category!=-1) {
					aerodromio.setEmptySlotsMinus1(Category);
					aeroplanaki.setKatastasi(1);//landing
					aeroplanaki.setLandingTime(97,aeroplanaki.getToP());
					aeroplanaki.setCategoryParked(Category,aerodromio.getCostofParking(Category));
					totalFlightsArrived--;
					System.out.println("mpike stin 8esi"+Category);
				}
				else {
					aeroplanaki.setKatastasi(0);
					System.out.println("den vre8ike adeia 8esi ");
				}
			}
		}
	}
	private int findPlaceforPlane(int j) {
		//leipei if katastasi!=1
		int k,Category;
		String FID;
			Plane aeroplanaki =aeroplana.get(j);
			if(aeroplanaki.getHrsFC()==-1) {
				aeroplanaki.setHrsFC(Hours);
				aeroplanaki.setMinsFC(Mins);
			}
				Category=findBestCategory(aeroplanaki.getToF(),aeroplanaki.getToP(),aeroplanaki.getLiM(),aerodromio,aeroplanaki.getYphresies());
				if(Category!=-1) {
					aerodromio.setEmptySlotsMinus1(Category);
					aeroplanaki.setKatastasi(1);//landing
					aeroplanaki.setLandingTime(97,aeroplanaki.getToP());
					aeroplanaki.setCategoryParked(Category,aerodromio.getCostofParking(Category));
					totalFlightsArrived--;
					System.out.println("mpike stin 8esi"+Category);
				}
				else {
					aeroplanaki.setKatastasi(0);
					System.out.println("den vre8ike adeia 8esi ");
				}
				return Category;
	}
	private static int findBestCategory(int ToF,int ToP,int Lim, AirportState aerodromio,int Yp) {
		if(Lim<=45 & ToF==1 & ToP>1 & aerodromio.getEmptySlots(1)>0 )return 1;
		else if(Lim<=90 & ToF==2 & ToP>1 & aerodromio.getEmptySlots(2)>0 )return 2;
		else if(Lim<=90 & ToF==1 & ToP>1 & aerodromio.getEmptySlots(3)>0)return 3;
		else if(Lim<=120 & ToP>1 & aerodromio.getEmptySlots(4)>0)return 4;
		else if(Lim<=180 & ToP==1 & aerodromio.getEmptySlots(5)>0)return 5;
		else if(Lim<=240 & aerodromio.getEmptySlots(6)>0 & Yp<=6)return 6; //++yphresies (mpikan)
		else if(Lim<=600 & ToF>1 & aerodromio.getEmptySlots(7)>0) return 7;
		else return -1;
	}
	
	private static int getRandomNumber(int top) {
		Random r = new Random();
		return r.nextInt((top - 0) + 1);
	}
	
	@FXML 
	private void initialize() {
		TypeofFlight.setValue("Epivatiki");
		TypeofFlight.setItems(ToF_List);
		TypeofPlane.setValue("Mono");
		TypeofPlane.setItems(ToP_List);
		//ZoneCSP.setVisible(true);

		
	}
	
	@FXML
	public void GatesMenuItem(ActionEvent event) { 
		GatesBox.display(aeroplana,aerodromio,ActivePlanes);
	}
	@FXML
	public void HoldingMenuItem(ActionEvent event) { 
		HoldingBox.display(aeroplana,aerodromio,ActivePlanes,totalFlightsArrived);
	}
	@FXML
	public void NextDepMenuItem(ActionEvent event) {
		NextDeparturesBox.display(aeroplana,aerodromio,ActivePlanes,FlightsLeavingin10mins);
	}
	@FXML
	public void FlightsMenuItem(ActionEvent event) {
		FlightsBox.display(aeroplana,aerodromio,ActivePlanes);
	}
	@FXML
	public void DelayedMenuItem(ActionEvent event) {
		DelayedBox.display(aeroplana,aerodromio,ActivePlanes,NumofDelFlights);
	}
	@FXML
	public void LoadMenuItem(ActionEvent event) {
		SCENARIO_IDargument=LoadBox.display("Which Scenario to Load?", "ki ekeina");
		
	}
	@FXML
	public void ExitApp(ActionEvent event) {
		System.out.println("You are exiting the app");
		Platform.exit();
		System.exit(0);
	} 
	@FXML
	public void StartTimer(ActionEvent event) {
		if(time!=null)time.stop();//otan patietai oxi gia prwti fora to start
		System.out.println("You just started the app");
		ZoneCSP.setVisible(false);
		ZoneCSP.managedProperty().bind(ZoneCSP.visibleProperty());
		main2(SCENARIO_IDargument);
		
		time = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			
		    @Override
		    public void handle(ActionEvent event) {
					Mins++;
					if(Mins==60) {
						Mins=0;
						Hours++;
					}
					if(Mins%5==0)MessageComponent.clear();
					TotalTimeTA.clear();
					TotalTimeTA.appendText("Total Time:\n"+Hours+":"+Mins);
					FlightsLeavingin10mins=NumofDelFlights=0;
					TotalMoneyTA.clear();
					TotalMoneyTA.appendText("Total Amount of Earnings:\n"+totalCost);
					TotalNumberESTA.clear();
					TotalNumberESTA.appendText("Total Number \r\nof Parking Slots:"+aerodromio.getAllEmptySlots());
					TotalFlightsArrivingTA.clear();
					TotalFlightsArrivingTA.appendText("Number of Flights \r\nthat are arriving:"+totalFlightsArrived);
					int j=0;
					while(j<ActivePlanes) {
						Plane aeroplanaki =aeroplana.get(j);
						if(aeroplanaki.getKatastasi()==1) {
							aeroplanaki.setLandingTime(aeroplanaki.getLandingTime()-1);
							if (aeroplanaki.getLandingTime()<=0) {
								aeroplanaki.setKatastasi(2);
								if(aeroplanaki.getKatastasi()==2) {
									System.out.println(aeroplanaki.getFID()+ " is Parked @ "+ aeroplanaki.getCategoryParked());
									int randomNumber=getRandomNumber(aeroplanaki.getLiM()*2);
									if (randomNumber>aeroplanaki.getLiM())aeroplanaki.setAtomicCost(2*aerodromio.getCostofParking(aeroplanaki.getCategoryParked())+aeroplanaki.getAtomicCost());
									else if(randomNumber<=aeroplanaki.getLiM()-25)aeroplanaki.setAtomicCost(0.8*aerodromio.getCostofParking(aeroplanaki.getCategoryParked())+aeroplanaki.getAtomicCost());
									else aeroplanaki.setAtomicCost(0.9*aerodromio.getCostofParking(aeroplanaki.getCategoryParked())+aeroplanaki.getAtomicCost());
									aeroplanaki.setFinalLiM(randomNumber);
									//meiwne kai ta 2 kai emfanize sta delayed osa FinalLiM>Lim
									}
							}
					}
						else if(aeroplanaki.getKatastasi()==2) {
							aeroplanaki.setLiM(aeroplanaki.getLiM()-1);
							aeroplanaki.setFinalLiM(aeroplanaki.getFinalLiM()-1);
							if (aeroplanaki.getFinalLiM()<=10 && aeroplanaki.getFinalLiM()>0)FlightsLeavingin10mins++;
							if (aeroplanaki.getFinalLiM()<=0) { 
								totalCost+=aeroplanaki.getAtomicCost();
								aerodromio.setEmptySlotsPlus1(aeroplanaki.getCategoryParked());
								aeroplana.remove(j);
								if (j+1!=ActivePlanes)j--;
								ActivePlanes--;
								//FlightsLeavingin10mins--;
								System.out.println("byeeee"+ j+"totalcost: "+totalCost);
								findPlaceforPlanes(15,aerodromio);
							}
							else if(aeroplanaki.getFinalLiM()>aeroplanaki.getLiM())NumofDelFlights++;
							
						}
					j++;	
				}
					Leavingin10TA.clear();
					Leavingin10TA.appendText("Number of Flights leaving \nin the next 10 minutes:"+FlightsLeavingin10mins);
					if(aerodromio.getKatastasi(1)!=0) {
						int a=aerodromio.getWholeSlots(1);
						int b=aerodromio.getEmptySlots(1);
						Pylh.clear();
						Pylh.appendText("PYLH\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
					}
					if(aerodromio.getKatastasi(2)!=0) {
						int a=aerodromio.getWholeSlots(2);
						int b=aerodromio.getEmptySlots(2);
						EmporikhPylh.clear();
						EmporikhPylh.appendText("EMPOREYMATIKH\r\nPYLH\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
					}
					if(aerodromio.getKatastasi(3)!=0) {
						int a=aerodromio.getWholeSlots(3);
						int b=aerodromio.getEmptySlots(3);
						ZoneA.clear();
						ZoneA.appendText("ZONE A\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
						
					}
					if(aerodromio.getKatastasi(4)!=0) {
						int a=aerodromio.getWholeSlots(4);
						int b=aerodromio.getEmptySlots(4);
						ZoneB.clear();
						ZoneB.appendText("ZONE B\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
					}
					if(aerodromio.getKatastasi(5)!=0){
						int a=aerodromio.getWholeSlots(5);
						int b=aerodromio.getEmptySlots(5);
						ZoneC.clear();
						ZoneC.appendText("ZONE C\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
					}
					if(aerodromio.getKatastasi(6)!=0) {
						int a=aerodromio.getWholeSlots(6);
						int b=aerodromio.getEmptySlots(6);
						GenikosXwrosStathmeusis.clear();
						GenikosXwrosStathmeusis.appendText("GENIKOS XWROS\n STATHMEYSIS\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
					}
					if(aerodromio.getKatastasi(7)!=0) {
						int a=aerodromio.getWholeSlots(7);
						int b=aerodromio.getEmptySlots(7);
						MD.clear();
						MD.appendText("MD\nTotal:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
					}
		    	
		    }
		}));
		time.setCycleCount(Timeline.INDEFINITE);
		time.play();
	} 
	@FXML
	public void SubmitNewFlight(ActionEvent event) {
		int ToF,ToP,Yp=0,CategoryGoes;
		if(Gas.isSelected())Yp+=1;
		if(Cleaning.isSelected())Yp+=5;
		if(Passengers.isSelected())Yp+=10;
		if(UnLoad.isSelected())Yp+=20;
		String FlightID=FIDTextField.getText();
		String Destination =DestTextField.getText();
		int LiM = Integer.parseInt(LiMTextField.getText());
		if(TypeofFlight.getValue()=="Emporeumatiki")ToF=2;
		else if(TypeofFlight.getValue()=="Idiwtiki")ToF=3;
		else ToF=1;
		if(TypeofPlane.getValue()=="Mono")ToP=1;
		else if(TypeofPlane.getValue()=="Turbo")ToP=2;
		else ToP=3;
		
		readPlanes(FlightID+","+Destination+","+ToF+","+ToP+","+LiM+","+Yp,54);
		CategoryGoes=findPlaceforPlane(ActivePlanes-1);
		if(CategoryGoes==-1)AnswerBox.display("Where does the Submitted plane go?", "It cannot go anywhere");
		else AnswerBox.display("Where does the Submitted plane go?", "It will go to the Category "+CategoryGoes);
	}
	
	@FXML
	public void A() {
		for(int i=0;i<10;i++) {
			TotalFlightsArrivingTA.appendText("10 "); 
		}
	}
	
	
	@Override
	public void start(Stage primaryStage){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("MediaLab Airport");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(e->Platform.exit());
		} catch(Exception e) {
			e.printStackTrace();
		}
		//main2("21");
	}
	
	public static void main(String[] args) {
		launch(args);
		//main2("21");
	}

}
