package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bean.LoginCredentials;

public interface LoginCredentialsRepository extends JpaRepository<LoginCredentials, String> {

	@Query("SELECT l from LoginCredentials l where l.roles=(SELECT r from Roles r where r.roleID=?1)")
	List<LoginCredentials> getUsers(int roleID);

}
