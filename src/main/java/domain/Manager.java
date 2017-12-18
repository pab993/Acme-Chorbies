package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor{

	// Attributes
	// =====================================================

	private String company;
	private String vat;
	private Double fee;
	
	// Constructor
	// =====================================================

	public Manager() {
		super();
	}

	// Getters & Setters
	// =====================================================

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getCompany() {
		return company;
	}

	
	public void setCompany(String company) {
		this.company = company;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}
	
	// Relationships
	// ====================================================================================
	
	private Collection<Event> events;


	@OneToMany(mappedBy="manager")
	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

	@Min(0)
	@NotNull
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	
}
