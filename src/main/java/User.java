import java.util.Date;
import java.util.concurrent.TimeUnit;

public class User {
	/**
	 * Fields related to ride file data
	 */
	public int userId;
	public int startStationId;
	public int endStationId;
	public Date startTime;
	public Date endTime;
	public long duration;

	/**
	 * Constructor
	 */
	public User(int u, int ss, int es, Date st, Date et) {
	this.userId = u;
	this.startStationId = ss;
	this.endStationId = es;
	this.startTime = st;
	this.endTime = et;
	getDuration();
	}
	
	/**
	 * set userId, startStationId, endStationId, startTime, and endTime
	 */
	public void setUserID(int newUserID) {
		this.userId = newUserID;
	}
	
	public void setFromStationID(int newFromStation) {
		this.startStationId = newFromStation;
	}
	
	public void setToStationID(int newToStation) {
		this.endStationId = newToStation;
	}
	
	public void setStartTime(Date newStartTime) {
		this.startTime = newStartTime;
	}
	
	public void setEndTime(Date newEndTime) {
		this.endTime = newEndTime;
	}
	
	
	/**
	 * Calculate the duration of the ride 
	 */
	private void getDuration() {
		long duration = this.endTime.getTime() - this.startTime.getTime();
		long intoMinutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
		this.duration = intoMinutes;
	}

}
