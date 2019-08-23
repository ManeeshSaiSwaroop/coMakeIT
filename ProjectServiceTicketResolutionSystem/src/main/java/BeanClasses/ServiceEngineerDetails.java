package BeanClasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;

@Entity
@NamedNativeQueries({@NamedNativeQuery(name="selectCorrespondingEngineers", query="SELECT * from ServiceEngineerDetails det where det.departmentID=?1", resultClass = ServiceEngineerDetails.class), 
	@NamedNativeQuery(name="updateServiceEngineerDetailsTableOnSubmit", query="UPDATE ServiceEngineerDetails det SET det.CurrentHighPriorityTicketID=?1, det.CurrentTicketPriorityID=?2 where det.ID=?3", resultClass = ServiceEngineerDetails.class)})
public class ServiceEngineerDetails {
	
	@Id
	private long ID;
	private int TotalTicketsWorkedOn;
	private String CurrentHighPriorityTicketID;
	
	@ManyToOne
	@JoinColumn(name="departmentID")
	private Departments departments;
	
	@ManyToOne
	@JoinColumn(name="CurrentTicketPriorityID")
	private Priorities priorities;
	
	@OneToOne
	@JoinColumn(name="username")
	private LoginCredentials credentials;
	
	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public int getTotalTicketsWorkedOn() {
		return TotalTicketsWorkedOn;
	}



	public void setTotalTicketsWorkedOn(int totalTicketsWorkedOn) {
		TotalTicketsWorkedOn = totalTicketsWorkedOn;
	}



	public String getCurrentHighPriorityTicketID() {
		return CurrentHighPriorityTicketID;
	}



	public void setCurrentHighPriorityTicketID(String currentHighPriorityTicketID) {
		CurrentHighPriorityTicketID = currentHighPriorityTicketID;
	}

	

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public Priorities getPriorities() {
		return priorities;
	}

	public void setPriorities(Priorities priorities) {
		this.priorities = priorities;
	}

	public LoginCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(LoginCredentials credentials) {
		this.credentials = credentials;
	}

	public ServiceEngineerDetails()
	{
		
	}
}
