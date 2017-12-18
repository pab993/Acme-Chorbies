
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Coordinate extends DomainEntity {

	// Attributes
	// ====================================================================================

	private String	country;
	private String	state;
	private String	province;
	private String	city;


	// Constructors
	// ====================================================================================

	public Coordinate() {
		super();
	}

	// Getters & setters
	// ====================================================================================

	@SafeHtml
	@NotBlank
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@SafeHtml
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@SafeHtml
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@SafeHtml
	@NotBlank
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	// Relationships
	// ====================================================================================

	private Chorbi			chorbi;
	private SearchTemplate	searchTemplate;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Chorbi getChorbi() {
		return chorbi;
	}

	public void setChorbi(Chorbi chorbi) {
		this.chorbi = chorbi;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public SearchTemplate getSearchTemplate() {
		return searchTemplate;
	}

	public void setSearchTemplate(SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
	}

}
