package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{

	// Devuelve eventos en los que el chorbi está registrado
	
	@Query("select e from Event e join e.chorbies c where c.id = ?1")
	Collection<Event> findAllRegistered(int id);

	//Devuelve los eventos pertenecientes a un manager
	@Query("select m.events from Manager m where m.id = ?1")
	Collection<Event> findEventsByManager(int idManager);
	
	//Devuelve los eventos que van ha organizarse en menos de un mes y tiene asientos libres
//	@Query("select e from Event e where e.moment > CURRENT_DATE and e.moment <?1 and (e.seatsNumber - e.chorbies.size) > 0 order by e.seatsNumber")
//	public Collection<Event> findByMonthToStartAndSeats(Date now);
	
	//Devuelve los eventos que van ha organizarse en menos de un mes y tiene asientos libres
	@Query("select e from Event e where (e.moment between ?1 and ?2) and ((e.seatsNumber - e.chorbies.size) > 0))")
	public Collection<Event> findByMonthToStartAndSeats(Date currentDate, Date oneMonth);

	//Devuelve los eventos pasados
	@Query("select e from Event e where e.moment < CURRENT_TIMESTAMP")
	public Collection<Event> findByPastEvents();
	
	//Devuelve los eventos futuros
	@Query("select e from Event e where (e.moment > ?1) and ((e.seatsNumber - e.chorbies.size) > 0))")
	public Collection<Event> findFutureEvents(Date oneMonth);
	
	//Ratio de eventos con un Dalem
	@Query("select (select 1.0*count(e1) from Event e1 where e1.dalems is not empty)/count(*) from Event e2")
	public Collection<Event> ratioEventWithADalem();
	
}
