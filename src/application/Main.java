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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


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
	@FXML
	private PieChart pieGXS;
	@FXML
	private PieChart pieMDSP;
	@FXML
	private PieChart pieP;
	@FXML
	private PieChart pieEP;
	@FXML
	private PieChart pieZoneC;
	@FXML
	private PieChart pieZoneB;
	@FXML
	private PieChart pieZoneA;
	AirportState aerodromio = new Airport();
	private static ArrayList<Plane> aeroplana = new ArrayList<Plane>(); 
	private static int ActivePlanes,totalFlightsArrived,FlightsLeavingin10mins,Mins,Hours,NumofDelFlights;
	private static double totalCost;
	private static String SCENARIO_IDargument;
	public void main2(String args) {
		//MessageComponent.setTextFill(Color.WHITE);
		//String style = "-fx-font-weight: bold;";
		//MessageComponent.setStyle(style);
		TotalTimeTA.clear();
		TotalTimeTA.appendText("Total Time:\n"+0+":"+0);
		aeroplana.clear();
		String SCENARIO_ID=args;
		String line;
		int i=0;
		ActivePlanes=totalFlightsArrived=Mins=Hours=0;
		totalCost=0.0;
		for (i=0;i<8;i++)aerodromio.setAll(i+1,0,0,"0",i,0);
		i=0;
			try {
				BufferedReader reader = new BufferedReader(new FileReader("multimedia/airport_"+SCENARIO_ID+".txt"));
				while((line=reader.readLine())!=null) {
					i=readAerodromio(line,i);
					i++;
				}
				reader.close();
				aerodromio.setN(i);
				reader = new BufferedReader(new FileReader("multimedia/setup_"+SCENARIO_ID+".txt"));
				i=0;
				while((line=reader.readLine())!=null) {
					readPlanes(line, i);
				}
				reader.close();
				MessageComponent.appendText("Just read from files: 'airport_"+SCENARIO_ID+".txt' and 'setup_"+ SCENARIO_ID+".txt' \n");
				MessageComponent.appendText("Just read "+ ActivePlanes+" planes from the text setup\n");
				findPlaceforPlanes(i,aerodromio);
			
			}
			catch(IOException e) { //NotFoundException
				MessageComponent.appendText("There are not such 'airport_"+SCENARIO_ID+".txt' and 'setup_"+ SCENARIO_ID+".txt' \n");
				System.err.format("There are not such 'airport_%s.txt' and 'setup_%s.txt' ",SCENARIO_ID,SCENARIO_ID);
				e.printStackTrace(); 
			}
			setInVisibilityforPies();	
			setChart();
	}
	public int readAerodromio(String line,int i) {
		String[] tempo;
		int Cat,ES,Cost;
		String SID;
		tempo=line.split(",");
		Cat=Integer.parseInt(tempo[0]);
		ES=Integer.parseInt(tempo[1]);
		Cost=Integer.parseInt(tempo[2]);
		SID=tempo[3];
		aerodromio.setAll(Cat,ES,Cost,SID,i,1);
		return i;
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
	
	public void setInVisibilityforPies() {
		if(aerodromio.getKatastasi(1)!=0)pieP.setVisible(true);
		else pieP.setVisible(false);
		if(aerodromio.getKatastasi(2)!=0)pieEP.setVisible(true);
		else pieEP.setVisible(false);
		if(aerodromio.getKatastasi(3)!=0)pieZoneA.setVisible(true);
		else pieZoneA.setVisible(false);
		if(aerodromio.getKatastasi(4)!=0)pieZoneB.setVisible(true); 
		else pieZoneB.setVisible(false);
		if(aerodromio.getKatastasi(5)!=0) {pieZoneC.setVisible(true);}
		else pieZoneC.setVisible(false);
		if(aerodromio.getKatastasi(6)!=0)pieGXS.setVisible(true);
		else pieGXS.setVisible(false);
		if(aerodromio.getKatastasi(7)!=0)pieMDSP.setVisible(true);
		else pieMDSP.setVisible(false);
	}
	public void setChart() {
		double fPylh=1,fEmpPylh=1,fZoneA=1,fZoneB=1,fZoneC=1,fGXS=1,fMDSP=1;
		if(aerodromio.getWholeSlots(1)!=0) fPylh=(aerodromio.getWholeSlots(1)-aerodromio.getEmptySlots(1))*1.0/aerodromio.getWholeSlots(1);
		if(aerodromio.getWholeSlots(2)!=0)fEmpPylh=(aerodromio.getWholeSlots(2)-aerodromio.getEmptySlots(2))*1.0/aerodromio.getWholeSlots(2);
		if(aerodromio.getWholeSlots(3)!=0)fZoneA=(aerodromio.getWholeSlots(3)-aerodromio.getEmptySlots(3))*1.0/aerodromio.getWholeSlots(3);
		if(aerodromio.getWholeSlots(4)!=0)fZoneB=(aerodromio.getWholeSlots(4)-aerodromio.getEmptySlots(4))*1.0/aerodromio.getWholeSlots(4);
		if(aerodromio.getWholeSlots(5)!=0)fZoneC=(aerodromio.getWholeSlots(5)-aerodromio.getEmptySlots(5))*1.0/aerodromio.getWholeSlots(5);
		if(aerodromio.getWholeSlots(6)!=0)fGXS=(aerodromio.getWholeSlots(6)-aerodromio.getEmptySlots(6))*1.0/aerodromio.getWholeSlots(6);
		if(aerodromio.getWholeSlots(7)!=0)fMDSP=(aerodromio.getWholeSlots(7)-aerodromio.getEmptySlots(7))*1.0/aerodromio.getWholeSlots(7);
		int a=aerodromio.getWholeSlots(1)-aerodromio.getEmptySlots(1);
		ObservableList<Data> listPylh=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(1),100-fPylh*100),
				new PieChart.Data("Taken : "+a,fPylh*100)
				);
		pieP.setData(listPylh);
		a=aerodromio.getWholeSlots(2)-aerodromio.getEmptySlots(2);
		ObservableList<Data> listEmpPylh=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(2),100-fEmpPylh*100),
				new PieChart.Data("Taken : "+a,fEmpPylh*100)
				);
		pieEP.setData(listEmpPylh);
		a=aerodromio.getWholeSlots(3)-aerodromio.getEmptySlots(3);
		ObservableList<Data> listZoneA=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(3),100-fZoneA*100),
				new PieChart.Data("Taken : "+a,fZoneA*100)
				);
		pieZoneA.setData(listZoneA);
		a=aerodromio.getWholeSlots(4)-aerodromio.getEmptySlots(4);
		ObservableList<Data> listZoneB=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(4),100-fZoneB*100),
				new PieChart.Data("Taken : "+a,fZoneB*100)
				);
		pieZoneB.setData(listZoneB);
		a=aerodromio.getWholeSlots(5)-aerodromio.getEmptySlots(5);
		ObservableList<Data> listZoneC=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(5),100-fZoneC*100),
				new PieChart.Data("Taken : "+a,fZoneC*100)
				);
		pieZoneC.setData(listZoneC);
		a=aerodromio.getWholeSlots(6)-aerodromio.getEmptySlots(6);
		ObservableList<Data> listGXS=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(6),100-fGXS*100),
				new PieChart.Data("Taken : "+a,fGXS*100)
				);
		pieGXS.setData(listGXS);
		a=aerodromio.getWholeSlots(7)-aerodromio.getEmptySlots(7);
		ObservableList<Data> listMDSP=FXCollections.observableArrayList(
				new PieChart.Data("Free : "+aerodromio.getEmptySlots(7),100-fMDSP*100),
				new PieChart.Data("Taken : "+a,fMDSP*100)
				);
		pieMDSP.setData(listMDSP);
	}
	private void findPlaceforPlanes(int i,AirportState aerodromio) {
		//if katast!=1
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
					MessageComponent.appendText(aeroplanaki.getFID()+ " will land to "+Category+"\n");
				}
				else {
					aeroplanaki.setKatastasi(0);
					MessageComponent.appendText("There is no place for "+aeroplanaki.getFID()+" to land\n");
				}
			}
		}
	}
	private int findPlaceforPlane(int j) {
		// if katastasi!=1
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
					MessageComponent.appendText(aeroplanaki.getFID()+ " will land to "+Category+"\n");
				}
				else {
					aeroplanaki.setKatastasi(0);
					MessageComponent.appendText("There is no place for "+aeroplanaki.getFID()+" to land\n");
				}
				return Category;
	}
	
	private static int findBestCategory(int ToF,int ToP,int Lim, AirportState aerodromio,int Yp) {
		if(Lim<=45 & ToF==1 & ToP>1 & aerodromio.getEmptySlots(1)>0 )return 1;
		else if(Lim<=90 & ToF==2 & ToP>1 & aerodromio.getEmptySlots(2)>0 )return 2;
		else if(Lim<=90 & ToF==1 & ToP>1 & aerodromio.getEmptySlots(3)>0)return 3;
		else if(Lim<=120 & ToP>1 & aerodromio.getEmptySlots(4)>0)return 4;
		else if(Lim<=180 & ToP==1 & aerodromio.getEmptySlots(5)>0)return 5;
		else if(Lim<=240 & aerodromio.getEmptySlots(6)>0 & Yp<=6)return 6; 
		else if(Lim<=600 & ToF>1 & aerodromio.getEmptySlots(7)>0) return 7;
		else return -1;
	}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
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
		MessageComponent.appendText("You are exiting the app\n");
		Platform.exit();
		System.exit(0);
	} 
	@FXML
	public void StartTimer(ActionEvent event) {
		MessageComponent.clear();
		if(time!=null)time.stop();//otan patietai oxi gia prwti fora to start
		MessageComponent.appendText("You just started the app\n");
		main2(SCENARIO_IDargument);
		
		time = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
			
		    @Override
		    public void handle(ActionEvent event) {
					Mins++;
					if(Mins==60) {
						Mins=0;
						Hours++;
					}
					if(Mins%5==0)MessageComponent.clear();//for cleaning the msgcmpnt
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
									MessageComponent.appendText(aeroplanaki.getFID()+ " is Parked @ "+ aeroplanaki.getCategoryParked()+"\n");
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
								MessageComponent.appendText(aeroplanaki.getFID()+" just left for "+aeroplanaki.getCity()+" and payed "+aeroplanaki.getAtomicCost()+"\n");
								aeroplana.remove(j);
								if (j+1!=ActivePlanes)j--;
								ActivePlanes--;
								//FlightsLeavingin10mins--;
								findPlaceforPlanes(15,aerodromio);
							}
							else if(aeroplanaki.getFinalLiM()>aeroplanaki.getLiM())NumofDelFlights++;
							
						}
					j++;	
				}
					Leavingin10TA.clear();
					Leavingin10TA.appendText("Number of Flights leaving \nin the next 10 minutes:"+FlightsLeavingin10mins);
					setChart();
		    }
		}));
		time.setCycleCount(Timeline.INDEFINITE);
		time.play();
	} 
	
	public void setTAText(int a,int b,TextArea TA) {
		TA.appendText("Total:"+a+"\nTaken:"+(a-b)+"\nFree:"+b);
	}
	
	@FXML
	public void SubmitNewFlight(ActionEvent event) {
		int ToF,ToP,Yp=0,CategoryGoes;
		if(Gas.isSelected())Yp+=1;
		if(Cleaning.isSelected())Yp+=5;
		if(Passengers.isSelected())Yp+=10;
		if(UnLoad.isSelected())Yp+=20;
		if(FIDTextField.getText().length()==0 | DestTextField.getText().length()==0 | !isInteger(LiMTextField.getText()))MessageComponent.appendText("Please provide a right submission\n");
		else {
		String FlightID=FIDTextField.getText();
		String Destination =DestTextField.getText();
		int LiM = Integer.parseInt(LiMTextField.getText());
		if(TypeofFlight.getValue()=="Emporeumatiki")ToF=2;
		else if(TypeofFlight.getValue()=="Idiwtiki")ToF=3;
		else ToF=1;
		if(TypeofPlane.getValue()=="Mono")ToP=1;
		else if(TypeofPlane.getValue()=="Turbo")ToP=2;
		else ToP=3;
		MessageComponent.appendText("You just inserted : "+FlightID+","+Destination+","+ToF+","+ToP+","+LiM+","+Yp+"\n");
		readPlanes(FlightID+","+Destination+","+ToF+","+ToP+","+LiM+","+Yp,54);
		CategoryGoes=findPlaceforPlane(ActivePlanes-1);
		if(CategoryGoes==-1)AnswerBox.display("Where does the Submitted plane go?", "It cannot go anywhere");
		else AnswerBox.display("Where does the Submitted plane go?", "It will go to the Category "+CategoryGoes);
		}
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
