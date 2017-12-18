
package domain;

import java.net.URL;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity {

	//Attributes
	//=====================================================

	private Date			createMoment;
	private String			subject;
	private String			text;
	private Collection<URL>	attachments;


	//Constructor
	//=====================================================

	public Chirp() {
		super();
	}

	//Getters & Setters
	//=====================================================

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getcreateMoment() {
		return createMoment;
	}

	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}

	@SafeHtml
	@NotBlank
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@SafeHtml
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@ElementCollection
	public Collection<URL> getAttachments() {
		return attachments;
	}

	public void setAttachments(Collection<URL> attachments) {
		this.attachments = attachments;
	}


	// Relationships
	// ====================================================================================

	private Actor	actorSender;
	private Actor	actorRecipient;


	@NotNull
	@Valid
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "actorSender_id")
	public Actor getActorSender() {
		return actorSender;
	}

	public void setActorSender(Actor actorSender) {
		this.actorSender = actorSender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "actor_id")
	public Actor getActorRecipient() {
		return actorRecipient;
	}

	public void setActorRecipient(Actor actorRecipient) {
		this.actorRecipient = actorRecipient;
	}

}
