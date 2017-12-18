
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "likeEntity", uniqueConstraints=
							{@UniqueConstraint(columnNames = {"chorbiSender", "chorbiRecipient"})})

public class Like extends DomainEntity {

	// Attributes
	// ====================================================================================

	private Date	createMoment;
	private String	comment;
	private Integer stars;


	// Constructors
	// ====================================================================================

	public Like() {
		super();
	}

	// Getters & setters
	// ====================================================================================

	@NotNull
	@Range(min = 0, max = 3)
	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreateMoment() {
		return this.createMoment;
	}

	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}

	@SafeHtml
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	// Relationships
	// ====================================================================================

	private Chorbi	chorbiSender;
	private Chorbi	chorbiRecipient;

	@JoinColumn(name="chorbiSender")
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Chorbi getChorbiSender() {
		return chorbiSender;
	}

	public void setChorbiSender(Chorbi chorbiSender) {
		this.chorbiSender = chorbiSender;
	}

	@JoinColumn(name="chorbiRecipient")
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Chorbi getChorbiRecipient() {
		return chorbiRecipient;
	}

	public void setChorbiRecipient(Chorbi chorbiRecipient) {
		this.chorbiRecipient = chorbiRecipient;
	}

}
