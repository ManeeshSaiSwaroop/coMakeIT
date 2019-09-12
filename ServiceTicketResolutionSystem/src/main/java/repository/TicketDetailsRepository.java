package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import bean.LoginCredentials;
import bean.TicketDetails;

public interface TicketDetailsRepository extends JpaRepository<TicketDetails, String> {

	@Query("SELECT t from TicketDetails t where t.details IN (select s from ServiceEngineerDetails s where s.departments = (select d from Departments d where departmentID =?1))  order by t.priorities ASC, t.dateOfIssue ASC")
	List<TicketDetails> getMostRecentlyLowerPriorityTicketAssignedServiceEngineer(int departmentID);

	@Query("SELECT t from TicketDetails t where t.credentials = (SELECT c from LoginCredentials c where c.username = ?1) order by field(ticketStatus, 'InProgress', 'Pending', 'Closed'), t.priorities DESC, -t.requestedEndDate ASC, t.dateOfIssue ASC")
	List<TicketDetails> getUserTickets(String username);

	@Query("SELECT t from TicketDetails t where t.details = (SELECT d from ServiceEngineerDetails d where d.credentials = (SELECT c from LoginCredentials c where c.username = ?1)) order by field(ticketStatus, 'InProgress', 'Pending', 'Closed'), t.priorities DESC, -t.requestedEndDate ASC, t.dateOfIssue ASC")
	List<TicketDetails> getServiceEngineerTickets(String username);

	@Query("SELECT t from TicketDetails t where t.ticketStatus!= 'Closed' and t.details = (SELECT d from ServiceEngineerDetails d where d.credentials = (SELECT c from LoginCredentials c where c.username = ?1)) order by t.priorities DESC, -t.requestedEndDate ASC, t.dateOfIssue ASC")
	List<TicketDetails> getServiceEngineerOpenTicketsInOrder(String username);

	@Transactional
	@Modifying
	@Query("UPDATE TicketDetails t SET t.credentials = ?1 where t.credentials = (SELECT c from LoginCredentials c where c.username=?2)")
	void updateUserDeleted(LoginCredentials credentials, String username);

	@Query("SELECT t from TicketDetails t where t.credentials = (SELECT l from LoginCredentials l where l.username=?1) and t.ticketStatus=?2")
	List<TicketDetails> getServiceEngineersListWorkingOnDeletedUser(String deletedUser, String ticketStatus);

	@Transactional
	@Modifying
	@Query("DELETE from TicketDetails t where t.credentials = (SELECT l from LoginCredentials l where l.username=?1) and t.ticketStatus!=?2 and t.ticketStatus!=?3")
	void deleteOpenTicketsWhichAreNotClosedOrInProgress(String deletedUser, String Closed, String InProgress);

	@Transactional
	@Modifying
	@Query("UPDATE TicketDetails t SET t.details = (SELECT s from ServiceEngineerDetails s where s.ID=?1) where t.details = (SELECT s from ServiceEngineerDetails s where s.ID=?2)")
	void replaceWithDeletedServiceEngineer(long deletedID, long ID);
}
