package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	// @Query("select min(a.sendChirps.size) from Actor a")
	// public Integer minMessagesSended();
	//
	// @Query("select max(a.sendChirps.size) from Actor a")
	// public Integer maxMessagesSended();
	//
	// @Query("select avg(a.sendChirps.size) from Actor a")
	// public Integer avgMessagesSended();

	//select c from Chirp c where c.actorRecipient.id= or (select a from Actor a where a.id = c.actorSender.id)
	@Query("select c from Chirp c where c.actorSender.id=?1 or c.actorRecipient.id=?1")
	public Collection<Chirp> findAllByChorbi(int actorId);

	@Query("select count(c) from Chirp c join c.actorRecipient r where (select count(c1) from Chirp c1 join c1.actorRecipient r1 where r1.id=r.id group by r1)<=ALL(select count(c2) from Chirp c2 join c2.actorRecipient r2 group by r2) group by r")
	public Integer minChirpsReceived();

	@Query("select count(c) from Chirp c join c.actorRecipient r where (select count(c1) from Chirp c1 join c1.actorRecipient r1 where r1.id=r.id group by r1)>=ALL(select count(c2) from Chirp c2 join c2.actorRecipient r2 group by r2) group by r")
	public Integer maxChirpsReceived();
	
	@Query("select 1.0*count(c)/(select count(ch) from Chorbi ch) from Chirp c")
	public Double avgChirps();
	
	@Query("select count(c) from Chirp c join c.actorSender r where (select count(c1) from Chirp c1 join c1.actorSender r1 where r1.id=r.id group by r1)<=ALL(select count(c2) from Chirp c2 join c2.actorSender r2 group by r2) group by r")
	public Integer minChirpsSends();
	
	@Query("select count(c) from Chirp c join c.actorSender r where (select count(c1) from Chirp c1 join c1.actorSender r1 where r1.id=r.id group by r1)>=ALL(select count(c2) from Chirp c2 join c2.actorSender r2 group by r2) group by r")
	public Integer maxChirpsSends();
	

}
