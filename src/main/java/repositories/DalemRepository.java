package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Dalem;

@Repository
public interface DalemRepository extends JpaRepository<Dalem, Integer>{
	

	@Query("select d from Dalem d where d.administrator.id = ?1 and d.event.id = ?2")
	Dalem findOneByAdministratorAndEvent(int actorId, int eventId);
	
	@Query("select d from Dalem d where d.event.id = ?1 and d.cancel = false")
	Collection<Dalem> findByEvent(int eventId);
	
}
