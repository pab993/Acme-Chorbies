
package forms;

import java.net.URL;
import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Chorbi;

public class ChirpForm {

	// Attributes
	// ====================================================================================

	private Date			createMoment;
	private String			subject;
	private String			text;
	private Collection<URL>	attachments;


	// Constructor
	// ====================================================================================

	public ChirpForm() {
		super();
	}

	// Getters & Setters
	// ====================================================================================

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getcreateMoment() {
		return createMoment;
	}

	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}

	@NotBlank
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

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

	private Chorbi	chorbiSender;
	private Chorbi	chorbiRecipient;


	@NotNull
	@Valid
	public Chorbi getChorbiSender() {
		return chorbiSender;
	}

	public void setChorbiSender(Chorbi chorbiSender) {
		this.chorbiSender = chorbiSender;
	}

	@NotNull
	@Valid
	public Chorbi getChorbiRecipient() {
		return chorbiRecipient;
	}

	public void setChorbiRecipient(Chorbi chorbiRecipient) {
		this.chorbiRecipient = chorbiRecipient;
	}

}
