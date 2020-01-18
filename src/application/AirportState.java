package application;
import java.util.Collection;
import java.util.Set;

public interface AirportState {
	
	/**
	 * It returns the total Slots of the particular Category
	 * 
	 * @author al_ve
	 * 
	 * @param Cat Parking Category
	 * @return int -total Slots
	 */
	public int getWholeSlots(int Cat);
	
	/**
	 * It sets the total Slots of the particular Category to the number given (WS).
	 * 
	 * @author al_ve
	 * 
	 * @param WS - the total Slots that will be set
	 * @param Cat Parking Category
	 */
	public void setWholeSlots(int WS,int Cat);
		/**
		 * It returns the Slot ID of the particular Category
		 * 
		 * @author al_ve
		 * 
		 * @param Cat Parking Category
		 * @return String - Slot ID
		 */
	  public String getSlotID(int Cat);
	  	/**
	  	 * It returns the Cost of Parking at this Category-Parking Slot
	  	 * @author al_ve
	  	 * 
	  	 * @param Cat Parking Category
	  	 * @return int - Cost of Parking
	  	 */
	  public int getCostofParking(int Cat);
	  
	  
	  	/**
	  	 * It returns the Category which has this particular Slot ID
	  	 * 
	  	 * @author al_ve
	  	 * @param SID Slot ID
	  	 * @return int - Parking Category
	  	 */
	  public int getCategory(String SID);
	  
	  /**
	   * It returns the Empty Slots of this particular Category
	   * 
	   * @author al_ve
	   * @param Cat Parking Category
	   * @return int - The Empty Slots of this Category
	   */
	  public int getEmptySlots(int Cat);
	  
	  
	  	/**
	  	 * It decreases by one the Empty Slots of a particular Category
	  	 * 
	  	 * @author al_ve
	  	 * @param Cat Parking Category
	  	 */
	  public void setEmptySlotsMinus1(int Cat);

	  	/**
	  	 * It increases by one the Empty Slots of a particular Category
	  	 * 
	  	 * @author al_ve
	  	 * @param Cat Parking Category
	  	 */
	  public void setEmptySlotsPlus1(int Cat);
	  
	  
	  	/**
	  	 * It returns all the empty parking slots of the Airport
	  	 * 
	  	 * @author al_ve
	  	 * @return int - Number of all Empty Parking Slots
	  	 */
	  public int getAllEmptySlots();
	  
	  
	  /**
	   * N is the number of existing parking categories and is set to the number i
	   * 
	   * @author al_ve
	   * @param i Number of existing parking Categories
	   */
	  public void setN(int i);
	  
	  /**
	   * It sets all the needed information for creating a parking slot
	   *
	   * @author al_ve
	   * @param cat Parking Category
	   * @param eS Slots of this Category
	   * @param cost Cost of Parking at one Spot
	   * @param sID Slot IDentifier
	   * @param i 
	   * @param Kat Katastasi
	   */
	  public void setAll(int cat, int eS, int cost, String sID, int i,int Kat);
	  
	  /**
	   * It returns the Katastasi of a particular Category
	   * 
	   * @author al_ve
	   * @param Cat Parking Category
	   * @return int - Katastasi
	   */
	  public int getKatastasi(int Cat);
}
