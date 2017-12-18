
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Transactional
@Service
public class ActorService {

	// Managed Repository 
	// ====================================================================================

	@Autowired
	private ActorRepository	actorRepository;


	// Constructor methods
	// ====================================================================================

	public ActorService() {
		super();
	}

	// Simple CRUDS methods 
	// ====================================================================================

//	public Actor create() {
//
//		return new Actor();
//	}

	public Actor findOne(int id) {

		Assert.isTrue(actorRepository.exists(id));

		return actorRepository.findOne(id);
	}

	public Collection<Actor> findAll() {

		Collection<Actor> actors;

		actors = actorRepository.findAll();
		Assert.notEmpty(actors);

		return actors;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor);

		Actor saved = actorRepository.save(actor);

		return saved;
	}

	public void delete(Actor actor) {
		Assert.notNull(actor, "Actor null");
		Assert.isTrue(actorRepository.exists(actor.getId()), "didn't found");

		actorRepository.delete(actor);
	}

	// Others bussines methods
	// ====================================================================================

	public Actor findByPrincipal() {
		Actor result;

		try {
			UserAccount userAccount = LoginService.getPrincipal();
			result = actorRepository.findByUserAccountId(userAccount.getId());

		} catch (Throwable exc) {
			result = null;

		}
		return result;
	}

	public Actor findByUserAccount(UserAccount userAccount) {
		Actor result;

		result = actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
	
	
	// Dashboard 
	// ====================================================================================

//	public Collection<Actor> findActorWithMoreMessagesSent(){
//		Collection<Actor> result;
//		
//		result = actorRepository.findActorWithMoreMessagesSent();
//		
//		return result;
//	}
//	
//	public Collection<Actor> findActorWithMoreMessagesReceived(){
//		Collection<Actor> result;
//		
//		result = actorRepository.findActorWithMoreMessagesReceived();
//		
//		return result;
//	}
}
