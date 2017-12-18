package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Dalem;

@Transactional
@Service
public class AdministratorService {

	// Managed Repository 
	// ====================================================================================

	@Autowired
	private AdministratorRepository	administratorRepository;


	// Constructor methods
	// ====================================================================================

	public AdministratorService() {
		super();
	}

	// Simple CRUDS methods 
	// ====================================================================================

//	public Actor create() {
//
//		return new Actor();
//	}

	public Administrator findOne(int id) {

		Assert.isTrue(administratorRepository.exists(id));

		return administratorRepository.findOne(id);
	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> administrators;

		administrators = administratorRepository.findAll();
		Assert.notEmpty(administrators);

		return administrators;
	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);

		Administrator saved = administratorRepository.save(administrator);

		return saved;
	}

	// Others bussines methods
	// ====================================================================================

	public Administrator findByPrincipal() {
		Administrator result;

		try {
			UserAccount userAccount = LoginService.getPrincipal();
			result = administratorRepository.findByUserAccountId(userAccount.getId());

		} catch (Throwable exc) {
			result = null;

		}
		return result;
	}

	public Administrator findByUserAccount(UserAccount userAccount) {
		Administrator result;

		result = administratorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
	
	public Administrator findByDalem(Dalem dalem) {
		Administrator result;

		result = administratorRepository.findByDalemId(dalem.getId());

		return result;
	}
	
}
