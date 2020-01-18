package application;
import java.util.*;

public class Plane implements PlaneState {
	private int TypeofFlight,TypeofPlane,LeavinginMins,Yphresies,Katastasi,LandingTime,CategoryParked,FinalLiM,MinsFC=-1,HrsFC=-1;
	private double AtomicCost=0;
	private String FlightID,City;
	private int N;
	
	@Override
	public void setAll(String F,String C,int ToF,int ToP,int LiM,int i,int Yp) {
		System.out.println(C);
		FlightID=F;
		City=C;
		TypeofFlight=ToF;
		TypeofPlane=ToP;
		LeavinginMins=LiM;
		Yphresies=Yp;
		Katastasi=0;
		
	}
	public int getHrsFC() {
		return HrsFC;
	}

	public void setHrsFC(int hrsFC) {
		HrsFC = hrsFC;
	}

	public int getMinsFC() {
		return MinsFC;
	}

	public void setMinsFC(int minsFC) {
		MinsFC = minsFC;
	}
	@Override
	public int getFinalLiM() {
		return FinalLiM;
	}
	@Override
	public void setFinalLiM(int finalLiM) {
		FinalLiM = finalLiM;
	}
	@Override
	public void setAtomicCost(double AC) {
		AtomicCost=AC;
	}
	@Override
	public double getAtomicCost() {
		return AtomicCost;
		}
	@Override
	public void setLandingTime(int LT,int ToP) {
		if (ToP==1) LandingTime=6;
		else if (ToP==2) LandingTime=4;
		else if(ToP==3) LandingTime=2;
	}
	@Override
	public int getLandingTime() {
		return LandingTime;
	}
	
	@Override
	public void setCategoryParked(int CP,int CostCP) {
		CategoryParked=CP;
		//calculate the atomic cost for yphresies
		AtomicCost=CostCP;
		if(Yphresies%5==1) {
			AtomicCost+=0.25*CostCP;
			Yphresies--;
		}
		Yphresies-=15;
		if(Yphresies>0) {
			AtomicCost+=0.05*CostCP;
			Yphresies-=20;
		}
		Yphresies+=15;
		Yphresies-=5;
		if(Yphresies>0) {
			AtomicCost+=0.02*CostCP;
			Yphresies-=10;
		}
		Yphresies+=5;
		if(Yphresies==5)AtomicCost+=0.02*CostCP;
	}
	@Override
	public int getCategoryParked() {
		return CategoryParked;
		}
	@Override
	public void setLandingTime(int LT) {
		LandingTime=LT;
	}
	
	@Override
	public int getYphresies() {
		return Yphresies;
	}
	
	@Override
	public int getKatastasi() {
		return Katastasi;
	}
	
	@Override
	public void setKatastasi( int Kat) {
		Katastasi=Kat;
	}
	
	@Override
	public int getN() {
		return N;
	}
	
	@Override
	public void setN(int i) {
		N=i;
	}
	
	@Override
	public int getToF(String FID) {
		for(int i=0;i<N;i++)
			if(FlightID==FID)return TypeofFlight;
		return -1;
	}
	@Override
	public int getToP(String FID) {
		for(int i=0;i<N;i++)
			if(FlightID==FID)return TypeofPlane;
		return -1;
	}
	@Override
	public int getLiM(String FID) {
		for(int i=0;i<N;i++)
			if(FlightID==FID)return LeavinginMins;
		return -1;
	} 
	@Override 
	public String getCity(String FID) {
		for(int i=0;i<N;i++)
			if(FlightID==FID)return City;
		return "-1";
	}

	@Override
	public String getFID() {
		return FlightID;
	}
	
	@Override
	public int getToF() {
		return TypeofFlight;
	}
	@Override
	public int getToP() {
		return TypeofPlane;
	}
	@Override
	public int getLiM () {
		return LeavinginMins;
	} 
	@Override
	public void setLiM(int LIM) {
		LeavinginMins=LIM;
	}
	@Override 
	public String getCity () {
		return City;
		}

	
	
	
	
}
