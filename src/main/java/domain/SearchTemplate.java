
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity {

	//Attributes
	//=====================================================

	private String	relationship;
	private int		age;
	private String	genre;
	private String	keyword;
	private Date	lastSearch;


	//Constructor
	//=====================================================

	public SearchTemplate() {
		super();
	}

	//Getters & Setters
	//=====================================================

	@SafeHtml
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	@Min(18)
	@Max(100)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@SafeHtml
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@SafeHtml
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastSearch() {
		return lastSearch;
	}

	public void setLastSearch(Date lastSearch) {
		this.lastSearch = lastSearch;
	}


	// Relationships
	// ====================================================================================

	private Collection<Chorbi>	chorbiesWanted;


	@ManyToMany
	public Collection<Chorbi> getChorbiesWanted() {
		return chorbiesWanted;
	}

	public void setChorbiesWanted(Collection<Chorbi> chorbiesWanted) {
		this.chorbiesWanted = chorbiesWanted;
	}

}
