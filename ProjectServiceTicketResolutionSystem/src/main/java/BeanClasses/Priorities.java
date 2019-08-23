package BeanClasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({@NamedNativeQuery(name="selectPriorities", query="SELECT * from Priorities p where p.priorityID!=0", resultClass = Priorities.class)})
public class Priorities {

	@Id
	private int priorityID;
	private String priorityName;
	
	
	
	public int getPriorityID() {
		return priorityID;
	}
	public void setPriorityID(int priorityID) {
		this.priorityID = priorityID;
	}
	public String getPriorityName() {
		return priorityName;
	}
	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}
	
	
	
	public Priorities()
	{
		
	}
	
	public Priorities(int priorityID, String priorityName)
	{
		this.priorityID = priorityID;
		this.priorityName = priorityName;
	}
}
