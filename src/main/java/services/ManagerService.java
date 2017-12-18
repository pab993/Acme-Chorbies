package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Manager;
import forms.ManagerForm;

@Transactional
@Service
public class ManagerService {

	// Repository
	// ======================================================================

	@Autowired
	private ManagerRepository managerRepository;

	// Services
	// ======================================================================

	// CRUD methods
	// =======================================================================

	public Manager findOne(int id) {
		Manager manager;

		manager = managerRepository.findOne(id);
		Assert.notNull(manager);

		return manager;
	}

	public Manager create() {

		Manager manager = new Manager();

		UserAccount userAccount = new UserAccount();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("MANAGER");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);

		manager.setUserAccount(userAccount);
		manager.setFee(0.0);

		return manager;
	}

	public Collection<Manager> findAll() {

		Collection<Manager> managers;

		managers = managerRepository.findAll();
		Assert.notNull(managers);

		return managers;
	}

	public Manager save(Manager manager) {
		Assert.notNull(manager);

		Manager saved = managerRepository.save(manager);
		Assert.isTrue(managerRepository.exists(saved.getId()));

		return saved;
	}

	// Other Bussiness methods
	// =======================================================================

	@Autowired
	private Validator validator;

	public Manager reconstruct(ManagerForm managerForm, BindingResult binding) {
		// TODO Auto-generated method stub
		Manager manager = create();

		manager.setName(managerForm.getName());
		manager.setSurname(managerForm.getSurname());
		manager.setEmail(managerForm.getEmail());
		manager.setPhoneNumber(managerForm.getPhone());
		manager.getUserAccount().setUsername(managerForm.getUsername());
		manager.getUserAccount().setPassword(
				new Md5PasswordEncoder().encodePassword(
						managerForm.getPassword(), null));
		manager.setCompany(managerForm.getCompany());
		manager.setVat(managerForm.getVat());

		if (!managerForm.getPassword().equals(managerForm.getRepeatPassword())) {
			managerForm.setRepeatPassword(null);
		}

		validator.validate(managerForm, binding);

		return manager;
	}

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Assert.isTrue(userAccount.getId() != 0);

		result = managerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Manager> managerByEvents() {
		Collection<Manager> res = managerRepository
				.managersOrderByEventNumber();
		Assert.notNull(res);
		return res;
	}
}
