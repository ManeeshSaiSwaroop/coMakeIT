package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bean.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

}
