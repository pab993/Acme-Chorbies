
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;


@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	// Attributes
	// ====================================================================================

	private String	banner;
	private int		hoursTemplate;
	private int		minutesTemplate;
	private int		secondsTemplate;
	private Double 	chorbiFee;
	private Double 	managerFee;


	//	private Timestamp	timeTemplate;

	// Constructors
	// ====================================================================================

	public ConfigurationSystem() {
		super();
	}

	// Getters & setters
	// ====================================================================================

	@URL
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@Range(min = 0, max = 24)
	@NotNull
	public int getHoursTemplate() {
		return hoursTemplate;
	}

	public void setHoursTemplate(int hoursTemplate) {
		this.hoursTemplate = hoursTemplate;
	}

	@Range(min = 0, max = 60)
	@NotNull
	public int getMinutesTemplate() {
		return minutesTemplate;
	}

	public void setMinutesTemplate(int minutesTemplate) {
		this.minutesTemplate = minutesTemplate;
	}

	@Range(min = 0, max = 60)
	@NotNull
	public int getSecondsTemplate() {
		return secondsTemplate;
	}

	public void setSecondsTemplate(int secondsTemplate) {
		this.secondsTemplate = secondsTemplate;
	}

	@Min(0)
	@NotNull
	public Double getChorbiFee() {
		return chorbiFee;
	}

	public void setChorbiFee(Double chorbiFee) {
		this.chorbiFee = chorbiFee;
	}
	
	@Min(0)
	@NotNull
	public Double getManagerFee() {
		return managerFee;
	}

	public void setManagerFee(Double managerFee) {
		this.managerFee = managerFee;
	}

	//	public Timestamp getTimeTemplate() {
	//		return timeTemplate;
	//	}
	//
	//	public void setTimeTemplate(Timestamp timeTemplate) {
	//		this.timeTemplate = timeTemplate;
	//	}
}
