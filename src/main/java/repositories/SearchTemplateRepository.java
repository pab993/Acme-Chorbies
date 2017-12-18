package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer>{

	@Query("select ch from Chorbi ch, Coordinate c where (ch.relationship = ?1) and (ch.age between ?2 -5 AND ?2 +5) and (ch.genre = ?3) and (ch.description like %?4% or ch.description = null) and ((c.city like %?5% or c.city = null) AND c.chorbi = ch) and ((c.country like %?6% or c.country = null) AND c.chorbi = ch) and ((c.state like %?7% or c.state = null) AND c.chorbi = ch) and ((c.province like %?8% or c.province = null) AND c.chorbi = ch) group by ch")
	Collection<Chorbi> search(String relationship, int age, String genre, String keyword, String city, String country, String state, String province);
}
