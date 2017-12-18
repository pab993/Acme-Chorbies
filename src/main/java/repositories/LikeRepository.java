package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer>{
	
//	@Query("select count(l1.chorbiRecipient) from Like l1 where (select count(l2.chorbiRecipient) from Like l2 where l2.chorbiRecipient = l1.chorbiRecipient) >= ALL(select count(l3.chorbiRecipient) from Like l3 where l3.chorbiRecipient = l1.chorbiRecipient)")
//	Double minLikesPerChorbi();

	//The minimum number of likes per chorbi
	@Query("select min(c.likesReceived.size) from Chorbi c")
	int minLikesPerChorbi();
	
	@Query("select max(c.likesReceived.size) from Chorbi c")
	int maxLikesPerChorbi();
	
	@Query("select avg(c.likesReceived.size) from Chorbi c")
	Double avgLikesPerChorbi();

	@Query("select l from Like l where l.chorbiSender.id = ?1 and l.chorbiRecipient.id = ?2")
	Like findOneBySenderAndRecipient(int senderId, int recipientId);

	@Query("select min(l.stars) from Like l join l.chorbiRecipient lc group by lc")
	Collection<Integer> minStarsByChorbi();

	@Query("select max(l.stars) from Like l join l.chorbiRecipient lc group by lc")
	Collection<Integer> maxStarsByChorbi();

	@Query("select avg(l.stars) from Like l join l.chorbiRecipient lc group by lc")
	Collection<Double> avgStarsByChorbi();

	@Query("select lc.name from Like l join l.chorbiRecipient lc group by lc order by avg(l.stars)")
	Collection<String> findAllChorbiesSortedByAVGStars();
	
	
	
}
