package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity{

	// Attributes
	// =====================================================
	
	private String title;
	private String description;
	private Date moment;
	private String picture;
	private Integer seatsNumber;

	// Constructor
	// =====================================================
	
	public Event() {
		super();
	}
	
	// Getters & Setters
	// =====================================================

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@NotNull
	@Min(1)
	public Integer getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(Integer seatsNumber) {
		this.seatsNumber = seatsNumber;
	}

	// Relationships
	// ====================================================================================

	private Collection<Chorbi> chorbies;
	private Collection<Dalem> dalems;
	private Manager manager;
	
	@ManyToMany()
	public Collection<Chorbi> getChorbies() {
		return chorbies;
	}

	public void setChorbies(Collection<Chorbi> chorbies) {
		this.chorbies = chorbies;
	}
	
	@OneToMany(mappedBy = "event")
	public Collection<Dalem> getDalems() {
		return dalems;
	}

	public void setDalems(Collection<Dalem> dalems) {
		this.dalems = dalems;
	}
	
	@ManyToOne(optional = false)
	public Manager getManager(){
		return manager;
	}
	
	public void setManager(Manager manager){
		this.manager = manager;
	}
}
