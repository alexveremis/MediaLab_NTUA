package application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class FirstClass {
	private static ArrayList<Plane> aeroplana = new ArrayList<Plane>(); 
	private static int ActivePlanes,totalFlightsArrived,FlightsLeavingin10mins,Mins,Hours;
	private static double totalCost;
	public static void main2(String args) {
		String SCENARIO_ID=args;
		String[] tempo;
		String line,FID,C,SID;
		int Cat,ES,Cost,i=0,ToF,ToP,LiM,Yp;
		long start = System.currentTimeMillis();
		ActivePlanes=totalFlightsArrived=Mins=Hours=0;
		AirportState aerodromio = new Airport();
		for (i=0;i<8;i++)aerodromio.setAll(i+1,0,0,"0",i,0);
		i=0;
			try {
				BufferedReader reader = new BufferedReader(new FileReader("airport_"+SCENARIO_ID+".txt"));
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
				reader = new BufferedReader(new FileReader("setup_"+SCENARIO_ID+".txt"));
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
					aeroplana.add(aeroplanaki)
;					ActivePlanes++;
				}
				reader.close();
				//i=readPlanes(args[1],i);
				System.out.println(ActivePlanes);
				findPlaceforPlane(i,aerodromio);
				//System.out.println((end-start)/5000);//ena lepto==5000msec edw
			
			}
			catch(IOException e) { //NotFoundException
				System.err.format("There are not such 'airport_%s.txt' and 'setup_%s.txt' ",SCENARIO_ID,SCENARIO_ID);
				e.printStackTrace();
			}
			/*
			while(true) {
				long end = System.currentTimeMillis();
				if ((end-start)/500>=1) {
					Mins++;
					if(Mins==60) {
						Mins=0;
						Hours++;
					}
					FlightsLeavingin10mins=0;
					start=System.currentTimeMillis();
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
									aeroplanaki.setLiM(randomNumber);
									//meiwne kai ta 2 kai emfanize sta delayed osa FinalLiM>Lim
									}
							}
					}
						else if(aeroplanaki.getKatastasi()==2) {
							aeroplanaki.setLiM(aeroplanaki.getLiM()-1);
							if (aeroplanaki.getLiM()<=20 && aeroplanaki.getLiM()>0)FlightsLeavingin10mins++;
							if (aeroplanaki.getLiM()<=0) { 
								totalCost+=aeroplanaki.getAtomicCost();
								aerodromio.setEmptySlotsPlus1(aeroplanaki.getCategoryParked());
								aeroplana.remove(j);
								if (j+1!=ActivePlanes)j--;
								ActivePlanes--;
								//FlightsLeavingin10mins--;
								System.out.println("byeeee"+ j+"totalcost: "+totalCost);
								findPlaceforPlane(15,aerodromio);
								
							}
							
						}
					j++;	
				}
					System.out.println("Total empty slots: "+aerodromio.getAllEmptySlots());
			}
		} */
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
			return i;
		}
	private static void findPlaceforPlane(int i,AirportState aerodromio) {
		//leipei if katastasi!=1
		int j,k,Category;
		String FID;
		for(j=0;j<ActivePlanes;j++) {
			Plane aeroplanaki =aeroplana.get(j);
			if(aeroplanaki.getKatastasi()==0) {
				Category=findBestCategory(aeroplanaki.getToF(),aeroplanaki.getToP(),aeroplanaki.getLiM(),aerodromio,aeroplanaki.getYphresies());
				if(Category!=-1) {
					aerodromio.setEmptySlotsMinus1(Category);
					aeroplanaki.setKatastasi(1);//landing
					aeroplanaki.setLandingTime(97,aeroplanaki.getToP());
					aeroplanaki.setCategoryParked(Category,aerodromio.getCostofParking(Category));
					totalFlightsArrived++;
					System.out.println("mpike stin 8esi"+Category);
				}
				else {
					aeroplanaki.setKatastasi(0);
					System.out.println("den vre8ike adeia 8esi ");
				}
			}
		}
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
	public static int getTimeMins() {
		return Mins;
	}
	public static int getTimeHrs() {
		return Hours;
	}
	public static double getTotalCost() {
		return totalCost;
	}
}
