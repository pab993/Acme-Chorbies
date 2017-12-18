package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chorbi extends Actor {

	// Attributes
	// =====================================================


	private String picture;
	private String description;
	private String relationship;
	private Date birth;
	private String genre;
	private int age;
	private Double monthlyFee;
	// Constructor
	// =====================================================

	public Chorbi() {
		super();
	}

	// Getters & Setters
	// =====================================================

	@SafeHtml
	@URL
	@NotBlank
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@SafeHtml
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@SafeHtml
	@NotBlank
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="you must to have +17")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@SafeHtml
	@NotBlank
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}


	// @Min(18)
	// public int getAge() {
	// return age;
	// }
	//
	// public void setAge(int age) {
	// this.age = age;
	// }
	
	
	/*public int getAge() {
>>>>>>> .r41
		Calendar birthC = Calendar.getInstance();
		birthC.setTime(birth);

		Calendar today = Calendar.getInstance();
		int diffYear = today.get(Calendar.YEAR) - birthC.get(Calendar.YEAR);
		int diffMonth = today.get(Calendar.MONTH) - birthC.get(Calendar.MONTH);
		int diffDay = today.get(Calendar.DAY_OF_MONTH)
				- birthC.get(Calendar.DAY_OF_MONTH);
		// Si está en ese año pero todavía no los ha cumplido
		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
			age = diffYear - 1;
		}
		return age;
	}*/
	
	@Min(18)
	@Max(100)
	public Integer getAge(){
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Min(0)
	@NotNull
	public Double getMonthlyFee() {
		return monthlyFee;
	}

	
	public void setMonthlyFee(Double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}


	// Relationships
	// ====================================================================================

	private SearchTemplate searchTemplate;
	private Collection<Like> likesReceived;
	private Collection<Like> likesSended;
	private Collection<Event> events;

	@Valid
	@OneToOne(optional = false)
	public SearchTemplate getSearchTemplate() {
		return this.searchTemplate;
	}

	public void setSearchTemplate(SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
	}

	@Valid
	@OneToMany(mappedBy = "chorbiRecipient")
	public Collection<Like> getLikesReceived() {
		return likesReceived;
	}

	public void setLikesReceived(Collection<Like> likesReceived) {
		this.likesReceived = likesReceived;
	}

	@Valid
	@OneToMany(mappedBy = "chorbiSender")
	public Collection<Like> getLikesSended() {
		return likesSended;
	}

	public void setLikesSended(Collection<Like> likesSended) {
		this.likesSended = likesSended;
	}

	@ManyToMany(mappedBy="chorbies")
	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}
}
