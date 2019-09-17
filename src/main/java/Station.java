import java.util.ArrayList;

public class Station {
	/**
	 * Station attributes
	 */

	public int id;
	public String name;
	public int bikes;
	public int pedelecs;
	public int availableDocks;
	public int mReq;
	public int capacity;
	public boolean kiosk;
	public String address;
	
	/**
	 * Station constructor
	 */
	public Station(int id, String name, int bikes, int pedelecs, int avDocks, int mReq, int capacity,
			boolean kiosk, String address) {
		this.id = id; 
		this.name = name;
		this.bikes = bikes; 
		this.pedelecs = pedelecs;
		this.availableDocks = avDocks;
		this.mReq = mReq;
		this.capacity = capacity;
		this.kiosk = kiosk;
		this.address = address;
	}
	
	/**
	* Function to set id of a station
	* @param id - integer id of station
	*/
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	* Set the attribute name for current (this) station selected
	* @param name - string name of station
	*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	* Set bikes count
	* @param bikes - int bike count
	*/
	public void setBikes(int bikes) {
		this.bikes = bikes;
	}
	
	/**
	* Set peds count
	* @param pedelecs - int peds count
	*/
	public void setPedelecs(int pedelecs) {
		this.pedelecs = pedelecs;
	}
	
	/**
	* Set available docks
	* @param availableDocks - integer number of available docks
	*/
	public void setAvailableDocks(int availableDocks) {
		this.availableDocks = availableDocks;
	}
	
	/**
	* Set maintenance requests count 
	* @param mReq = mainteneance request count 
	*/
	public void setMaintenanceRequests(int mReq) {
		this.mReq = mReq;
	}
	
	/**
	* Set capacity count 
	* @param capacity - integer capacity count
	*/
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	* Set if station selected has a kiosk
	* @param kiosk - boolean for whether a kiosk exists or not
	*/
	public void setHasKiosk(boolean kiosk) {
		this.kiosk = kiosk;
	}
	
	/**
	* Set address for station 
	* @param address - string station address
	*/
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	


	
	
	
	
 
}
