package bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceEngineerDetails")
public class ServiceEngineerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	private int TotalTicketsWorkedOn;
	private String CurrentHighPriorityTicketID;

	@ManyToOne
	@JoinColumn(name = "departmentID")
	private Departments departments;

	@ManyToOne
	@JoinColumn(name = "CurrentTicketPriorityID")
	private Priorities priorities;

	@OneToOne
	@JoinColumn(name = "username")
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

	public ServiceEngineerDetails() {
	}
}
