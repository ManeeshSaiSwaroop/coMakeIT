package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TicketDetails")
public class TicketDetails {

	@Override
	public String toString() {
		return "TicketDetails [ticketID=" + ticketID + ", dateOfIssue=" + dateOfIssue + ", requestedEndDate="
				+ requestedEndDate + ", problemDescription=" + problemDescription + ", ticketStatus=" + ticketStatus
				+ ", startTime=" + startTime + ", completionTime=" + completionTime + ", details=" + details
				+ ", priorities=" + priorities + ", credentials=" + credentials + "]";
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid")
	@Column(updatable = false, nullable = false, name = "ticketID")
	private String ticketID;
	private String dateOfIssue;
	private String requestedEndDate;
	private String problemDescription;
	private String ticketStatus;
	private String startTime;
	private String completionTime;

	@ManyToOne
	@JoinColumn(name = "ID")
	private ServiceEngineerDetails details;

	@ManyToOne
	@JoinColumn(name = "ticketPriorityID")
	private Priorities priorities;

	@ManyToOne
	@JoinColumn(name = "customerUsername")
	private LoginCredentials credentials;

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getRequestedEndDate() {
		return requestedEndDate;
	}

	public void setRequestedEndDate(String requestedEndDate) {
		this.requestedEndDate = requestedEndDate;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(String completionTime) {
		this.completionTime = completionTime;
	}

	public ServiceEngineerDetails getDetails() {
		return details;
	}

	public void setDetails(ServiceEngineerDetails details) {
		this.details = details;
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

}
