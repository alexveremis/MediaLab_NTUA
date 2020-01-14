package application;
import java.util.Collection;
import java.util.Set;

public interface PlaneState {
	public void setAll(String F,String C,int ToF,int ToP,int LiM,int i,int Yp);
	public int getN();
	public void setN(int i);
	public int getToF(String FID);
	public int getToP(String FID);
	public int getLiM(String FID);
	public String getCity(String FID);
	public String getFID();
	public int getToF();
	public int getToP();
	public int getLiM();
	public void setLiM(int LIM);
	public String getCity();
	public int getYphresies();
	public int getKatastasi();
	public void setKatastasi(int Kat);
	public int getLandingTime();
	public void setLandingTime(int LT);
	public void setLandingTime(int LT,int ToP);
	public void setCategoryParked(int CP,int CostCP);
	public int getCategoryParked();
	public void setAtomicCost(double AC) ;
	public double getAtomicCost();
	public void setFinalLiM(int finalLiM);
	public int getFinalLiM();
}
