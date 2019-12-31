package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bean.ServiceEngineerDetails;

public interface ServiceEngineerDetailsRepository extends JpaRepository<ServiceEngineerDetails, Long> {

	@Query("SELECT s from ServiceEngineerDetails s where s.departments=(select d from Departments d where d.departmentID=?1)")
	List<ServiceEngineerDetails> getCorrespondingEngineers(int departmentID);

	@Query("SELECT s from ServiceEngineerDetails s where s.ID=?1")
	ServiceEngineerDetails getServiceEngineersListWorkingOnDeletedUser(long ID);

	@Query("SELECT COUNT(s) from ServiceEngineerDetails s where s.departments = (SELECT d from Departments d where d.departmentID=?1)")
	int getCountOfEngineersForDepartment(int departmentID);
}
