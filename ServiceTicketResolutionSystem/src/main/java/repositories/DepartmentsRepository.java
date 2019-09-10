package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Beans.Departments;

public interface DepartmentsRepository extends JpaRepository<Departments, Integer> {

	@Query("SELECT d from Departments d where d.departmentName=?1")
	Departments findByName(String departmentName);
}
