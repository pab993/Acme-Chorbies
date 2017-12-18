
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	//Find the userAccount by the Id
	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	//The list of chorbies, sorted by the number of likes they have got.
	@Query("select l.chorbiSender.name from Like l group by l.chorbiSender order by count(l.chorbiSender) DESC")
	Collection<Chorbi> findChorbiesByLikes();

	//A listing with the number of chorbies per country and city.
	@Query("select c.chorbi from Coordinate c group by c.country")
	Collection<Chorbi> listChorbiesByCountry();

	@Query("select c.chorbi from Coordinate c group by c.city")
	Collection<Chorbi> listChorbiesByCity();

//	//The minimum, the maximum, and the average ages of the chorbies.
//	@Query("select min(c.age), avg(c.age), max(c.age) from Chorbi c")
//	Collection<Double> minAvgMaxChorbiesAges();
	
	//The minimum ages of the chorbies
	@Query("select min(c.age) from Chorbi c")
	Double minChorbiesAges();
	
	//The maximun ages of the chorbies
	@Query("select max(c.age) from Chorbi c")
	Double maxChorbiesAges();
	
	//The average ages of the chorbies
	@Query("select avg(c.age) from Chorbi c")
	Double avgChorbiesAges();

	//The  ratio  of  chorbies  who  have  not  registered  a  credit  card  or  have regis-tered an invalid credit card.
	@Query("select 100.0*count(cc.actor)/(select count(c) from Actor c) from CreditCard cc where cc.actor != null")
	Double ratioChorbiesWithCreditCard();
	
//	//The  ratio  of  chorbies  who  have  not  registered  a  credit  card  or  have regis-tered an invalid credit card.
//	@Query("select 100.0*count(cc.chorbi)/(select count(c) from Chorbi c) from CreditCard cc where cc.chorbi != null")
//	Double ratioChorbiesWithCreditCard();

	@Query("select 100.0*count(cc.actor)/(select count(c)-1 from Actor c) from CreditCard cc where cc.actor != null")
	Double ratioChorbiesWithoutCreditCard();
	
//	@Query("select 100.0*count(cc.actor)/(select count(c) from Actor c) from CreditCard cc where cc.actor = null")
//	Double ratioChorbiesWithoutCreditCard();

	//The ratios of chorbies who search for activities, friendship, and love.
	@Query("select 100.0*count(st)/(select count(c) from Chorbi c) from SearchTemplate st where st.relationship = 'ACTIVITIES'")
	Double ratioChorbiesFindActivities();
		
	@Query("select 100.0*count(st)/(select count(c) from Chorbi c) from SearchTemplate st where st.relationship = 'FRIENDSHIP'")
	Double ratioChorbiesFindFriendship();

	@Query("select 100.0*count(st)/(select count(c) from Chorbi c) from SearchTemplate st where st.relationship = 'LOVE'")
	Double ratioChorbiesFindLove();
	
	@Query("select c from Chorbi c where c.userAccount.enabled = true ")
	Collection<Chorbi> findAllNotBaned();
	
	@Query("select c from Chorbi c join c.likesSended ls where c.userAccount.enabled = true and ls.chorbiRecipient.id = ?1")
	Collection<Chorbi> findAllNotBanedLiked(int chorbiId);
	@Query("select c.actorRecipient from Chirp c join c.actorRecipient r where (select count(c1) from Chirp c1 join c1.actorRecipient r1 where r1.id=r.id group by r1)>=ALL(select count(c2) from Chirp c2 join c2.actorRecipient r2 group by r2) group by r")
	public Collection<Chorbi> maxChirpsReceived();
	
	@Query("select c.actorSender from Chirp c join c.actorSender r where (select count(c1) from Chirp c1 join c1.actorSender r1 where r1.id=r.id group by r1)>=ALL(select count(c2) from Chirp c2 join c2.actorSender r2 group by r2) group by r")
	public Collection<Chorbi> maxChirpsSends();

	@Query("select c from Chorbi c order by c.events.size")
	public Collection<Chorbi> chorbisOrderByEvents();
}
