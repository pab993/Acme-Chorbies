package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

	//Find the userAccount by the Id
	@Query("select c from Manager c where c.userAccount.id = ?1")
	Manager findByUserAccountId(int userAccountId);
	
	
	@Query("select m from Manager m order by m.events.size")
	Collection<Manager> managersOrderByEventNumber();
	
}
