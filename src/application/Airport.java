package application;
import java.util.*;

public class Airport implements AirportState{
	private int[] Category=new int[8],EmptySlots=new int[8],CostofParking=new int[8],Katastasi=new int [8],WholeSlots=new int[8];
	private String[] SlotID=new String[8];
	private int N;
	
	public void setAll(int Cat,int ES,int Cost ,String SID,int i,int Kat) {
		//System.out.println(Cat);
		Category[Cat-1]=Cat;
		EmptySlots[Cat-1]=ES;
		CostofParking[Cat-1]=Cost;
		SlotID[Cat-1]=SID;
		Katastasi[Cat-1]=Kat;
		WholeSlots[Cat-1]=ES;
		//i Category[Cat]=Cat;
	}
	public int getWholeSlots(int Cat) {
		if(Katastasi[Cat-1]==1)return WholeSlots[Cat-1];
		return -1;
	}


	public void setWholeSlots(int WS,int Cat) {
		if(Katastasi[Cat-1]==1)WholeSlots[Cat-1] =WS;
	}


	public int getKatastasi(int Cat) {
		return Katastasi[Cat-1];
	}

	
	public void setN(int i) { 
		N=8; //xreiazetai gia ta forloops
	}
	
	@Override
	public String getSlotID(int Cat) { //isws 8elei Category[Cat-1] 8elei 0 se oses den yparxoyn omws
		if(Katastasi[Cat-1]==1)return SlotID[Cat-1];
		return "-1";
	}
	@Override
	public int getCostofParking(int Cat) {
		if(Katastasi[Cat-1]==1)return CostofParking[Cat-1];
		return -1;
	}
	@Override
	public int getCategory(String SID) {
		for(int i=0;i<N;i++)
			if(SlotID[i]==SID)return Category[i];
		return -1;
	}
	@Override 
	public int getEmptySlots(int Cat) {
		if(Katastasi[Cat-1]==1)return EmptySlots[Cat-1];
		return -1;
	}
	@Override
	public void setEmptySlotsMinus1(int Cat) {
			if(Katastasi[Cat-1]==1)EmptySlots[Cat-1]-=1;
	}
	@Override
	public void setEmptySlotsPlus1(int Cat) {
			if(Katastasi[Cat-1]==1)EmptySlots[Cat-1]++;
	}
	
	@Override
	public int getAllEmptySlots() {
		int sum=0;
		for(int i=0;i<N;i++)sum=sum+EmptySlots[i];
		return sum;
	}
}
