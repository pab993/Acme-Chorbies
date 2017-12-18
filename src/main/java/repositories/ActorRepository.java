package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{
	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);
	
//	@Query("select a from Actor a where a.sendMessages.size= (select max(a.sendMessages.size) from Actor a)")
//	Collection<Actor> findActorWithMoreMessagesSent();
//	
//	@Query("select a from Actor a where a.receivedMessages.size= (select max(a.receivedMessages.size) from Actor a)")
//	Collection<Actor> findActorWithMoreMessagesReceived();
	
//	@Query("select a from Actor a order by a.sendChirps.size DESC")
//	Collection<Actor> findActorWithMoreMessagesSent();
	
//	@Query("select a from Actor a order by a.receivedChirps.size DESC")
//	Collection<Actor> findActorWithMoreMessagesReceived();
	
	@Query("select a from Actor a where a.id=?1")
	Actor findOne2(int actorId);
	
	
}
