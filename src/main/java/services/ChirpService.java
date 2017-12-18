package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import forms.ChirpForm;

@Transactional
@Service
public class ChirpService {

	// Managed Repository
	// ====================================================================================

	@Autowired
	private ChirpRepository chirpRepository;

	// Managed Services
	// ====================================================================================

	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private ActorService actorService;

	// Constructor methods
	// ====================================================================================

	public ChirpService() {
		super();
	}

	// Simple CRUDS methods
	// ====================================================================================

	public Chirp findOne(int chirpId) {
		Assert.isTrue(chirpId != 0);
		Chirp result;

		result = chirpRepository.findOne(chirpId);

		return result;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> result;

		result = chirpRepository.findAll();

		return result;
	}

	public Chirp create() {
		Chirp result;
		Actor principal;
		Date createMoment;

		principal = actorService.findByPrincipal();
		Assert.notNull(principal);

		createMoment = new Date(System.currentTimeMillis());
		result = new Chirp();
		result.setCreateMoment(createMoment);
		result.setActorSender(principal);

		return result;
	}

	public Chirp save(Chirp chirp) {
		Assert.notNull(chirp);
		Chirp result;
		Actor principal;

		principal = actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(chirp.getActorRecipient());
		Assert.notNull(chirp.getActorSender());
		Assert.notNull(chirp.getSubject());
		Assert.notNull(chirp.getText());
		result = chirpRepository.save(chirp);

		return result;
	}
	
	public void saveBroadcast(Collection<Chorbi> chorbies, Chirp chirp) {
		Assert.notEmpty(chorbies);
		Assert.notNull(chirp);
		
		Actor principal;
		principal = actorService.findByPrincipal();
		
		Chirp chirpBroadcast = create();
		chirpBroadcast.setSubject("BROADCAST:" + chirp.getSubject());
		chirpBroadcast.setText(chirp.getText());
		chirpBroadcast.setAttachments(chirp.getAttachments());
		
		Assert.notNull(principal);
		Assert.notNull(chirpBroadcast.getActorSender());
		Assert.notNull(chirpBroadcast.getSubject());
		Assert.notNull(chirpBroadcast.getText());
		
		for(Chorbi c : chorbies){
			chirpBroadcast.setActorSender(c);
			chirpBroadcast.setActorRecipient(c);
			chirpRepository.save(chirpBroadcast);
		}

	}
	
	public void saveBroadcast(Collection<Chorbi> chorbies) {
		Assert.notEmpty(chorbies);
		
		Actor principal;
		principal = actorService.findByPrincipal();
		
		Chirp chirpBroadcast = create();
		chirpBroadcast.setSubject("BROADCAST MESSAGE");
		chirpBroadcast.setText("The event will be modified or deleted");
		
		Assert.notNull(principal);
		Assert.notNull(chirpBroadcast.getActorSender());
		Assert.notNull(chirpBroadcast.getSubject());
		Assert.notNull(chirpBroadcast.getText());
		
		for(Chorbi c : chorbies){
			chirpBroadcast.setActorSender(principal);
			chirpBroadcast.setActorRecipient(c);
			chirpRepository.save(chirpBroadcast);
		}

	}

	public void delete(Chirp chirp) {
		Assert.notNull(chirp);
		Chorbi chorbi;

		chorbi = chorbiService.findByPrincipal();
		Assert.notNull(chorbi);
		Assert.isTrue(chorbi.getId() == chirp.getActorSender().getId(),
				"You cant delete this chirp");

		chirpRepository.delete(chirp);
	}

	// Others bussines methods
	// ====================================================================================

	public Collection<Chirp> findAllByChorbi(int actorId) {
		Collection<Chirp> result;

		result = chirpRepository.findAllByChorbi(actorId);

		return result;
	}

	public void checkIfChorbi() {
		Chorbi principal;

		principal = chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isInstanceOf(Chorbi.class, principal);
	}

	// Dashboard
	// ====================================================================================

	// public Integer minMessagesSended() {
	//
	// return messageRepository.minMessagesSended();
	// }
	//
	// public Integer maxMessagesSended() {
	//
	// return messageRepository.maxMessagesSended();
	// }
	//
	// public Integer avgMessagesSended() {
	//
	// return messageRepository.avgMessagesSended();
	// }

	// Ancillary methods
	// ====================================================================

	@Autowired
	private Validator validator;

	public Chirp reconstruct(ChirpForm chirpForm, BindingResult binding) {

		Chirp chirpRec = new Chirp();

		chirpRec.setActorSender(actorService.findByPrincipal());
		chirpRec.setCreateMoment(new Date(System.currentTimeMillis() - 10));

		chirpRec.setActorRecipient(chirpForm.getChorbiRecipient());
		chirpRec.setSubject(chirpForm.getSubject());
		chirpRec.setText(chirpForm.getText());
		chirpRec.setAttachments(chirpForm.getAttachments());

		validator.validate(chirpRec, binding);

		return chirpRec;
	}

	public int minChirpsReceived() {

		return chirpRepository.minChirpsReceived();
	}

	public int maxChirpsReceived() {
		return chirpRepository.maxChirpsReceived();
	}

	public int minChirpsSends() {
		return chirpRepository.minChirpsSends();
	}
	public int maxChirpsSends() {
		return chirpRepository.maxChirpsSends();
	}

	public Double avgChirps() {
		
		return chirpRepository.avgChirps();
	}
}
