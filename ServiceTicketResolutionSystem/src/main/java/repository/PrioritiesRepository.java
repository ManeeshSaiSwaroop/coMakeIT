package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bean.Priorities;

public interface PrioritiesRepository extends JpaRepository<Priorities, Integer> {

	@Query(value = "select CONCAT('', SEC_TO_TIME(AVG(TIMESTAMPDIFF(SECOND, startTime, completionTime)))) from TicketDetails where ticketStatus = ?1 and ticketPriorityID=?2", nativeQuery = true)
	String getAverageSeverityForGivenPriority(String ticketStatus, int ticketPriorityID);

	@Query(value = "select CONCAT('', SEC_TO_TIME(AVG(TIMESTAMPDIFF(SECOND, startTime, completionTime)))) from TicketDetails where ticketStatus = ?1 and ID=?2", nativeQuery = true)
	String getReportStatisticsPerServiceEngineer(String ticketStatus, Long ID);

	@Query(value = "select ticketID, CONCAT('', SEC_TO_TIME(AVG(TIMESTAMPDIFF(SECOND, dateOfIssue, sysdate())))) from TicketDetails where ticketStatus!= ?1 and ID=(SELECT ID from ServiceEngineerDetails where username=?2) GROUP BY ID, ticketID", nativeQuery = true)
	List<Object[]> getAgeOfOpenTickets(String ticketStatus, String username);
}
