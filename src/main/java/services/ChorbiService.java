package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chorbi;
import domain.Coordinate;
import domain.Event;
import domain.SearchTemplate;
import forms.ChorbiEditForm;
import forms.ChorbiForm;

@Transactional
@Service
public class ChorbiService {

	// Supporting repository
	// ====================================================================

	@Autowired
	private ChorbiRepository chorbiRepository;
	
	@Autowired
	private ConfigurationSystemService configurationSystemService;
	// Constructor
	// ====================================================================

	public ChorbiService() {
		super();
	}

	// Supporting services
	// ====================================================================
	@Autowired
	private SearchTemplateService searchTemplateService;
	@Autowired
	private CoordinateService coordinateService;
	@Autowired
	private ActorService actorService;

	// Simple CRUD methods
	// ====================================================================

	public Chorbi create() {

		Chorbi chorbi = new Chorbi();

		UserAccount userAccount = new UserAccount();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("CHORBI");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);

		chorbi.setUserAccount(userAccount);

		SearchTemplate searchTemplate = searchTemplateService.create();
		SearchTemplate savedSearchTemplate = searchTemplateService
				.save2(searchTemplate);
		chorbi.setSearchTemplate(savedSearchTemplate);
		chorbi.setMonthlyFee(0.0);

		return chorbi;
	}

	public Chorbi save(Chorbi chorbi) {

		Assert.notNull(chorbi);

		Chorbi saved = chorbiRepository.save(chorbi);
		Assert.isTrue(chorbiRepository.exists(saved.getId()));

		return saved;
	}

	// Ancillary methods
	// ====================================================================

	public Chorbi findOne(int chorbiId) {
		Assert.isTrue(chorbiRepository.exists(chorbiId));
		Assert.isTrue(chorbiId != 0);
		Chorbi result;

		result = chorbiRepository.findOne(chorbiId);
		// Assert.notNull(result);
		return result;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;

		result = chorbiRepository.findAll();

		return result;
	}

	// Ancillary methods
	// ====================================================================

	@Autowired
	private Validator validator;

	public Chorbi reconstruct(ChorbiForm chorbiForm, BindingResult binding) {
		// TODO Auto-generated method stub
		Chorbi chorbi = create();

		chorbi.setName(chorbiForm.getName());
		chorbi.setSurname(chorbiForm.getSurname());
		chorbi.setEmail(chorbiForm.getEmail());
		chorbi.setPhoneNumber(chorbiForm.getPhone());
		chorbi.getUserAccount().setUsername(chorbiForm.getUsername());
		chorbi.getUserAccount().setPassword(
				new Md5PasswordEncoder().encodePassword(
						chorbiForm.getPassword(), null));
		chorbi.setPicture(chorbiForm.getPicture());
		chorbi.setRelationship(chorbiForm.getRelationship());
		chorbi.setGenre(chorbiForm.getGene());
		chorbi.setDescription(chorbiForm.getDescription());
		chorbi.setBirth(chorbiForm.getBirth());
		chorbi.setAge(calculaEdad(chorbiForm.getBirth()));

		if (!chorbiForm.getPassword().equals(chorbiForm.getRepeatPassword())) {
			chorbiForm.setRepeatPassword(null);
		}

		if (calculaEdad(chorbiForm.getBirth()) < 18)
			chorbiForm.setBirth(null);

		validator.validate(chorbiForm, binding);

		return chorbi;
	}

	public Chorbi findByPrincipal() {
		Chorbi result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Assert.isTrue(userAccount.getId() != 0);

		result = chorbiRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public int calculateBirth(Date birth) {
		Calendar birthC = Calendar.getInstance();
		birthC.setTime(birth);

		Calendar today = Calendar.getInstance();
		int diffYear = today.get(Calendar.YEAR) - birthC.get(Calendar.YEAR);
		int diffMonth = today.get(Calendar.MONTH) - birthC.get(Calendar.MONTH);
		int diffDay = today.get(Calendar.DAY_OF_MONTH)
				- birthC.get(Calendar.DAY_OF_MONTH);
		// Si está en ese año pero todavía no los ha cumplido
		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
			diffYear = diffYear - 1;
		}
		return diffYear;
	}

	public Collection<Chorbi> listChorbiesByCountry() {
		Collection<Chorbi> result;

		result = chorbiRepository.listChorbiesByCountry();

		return result;
	}

	public Collection<Chorbi> listChorbiesByCity() {
		Collection<Chorbi> result;

		result = chorbiRepository.listChorbiesByCity();

		return result;
	}

	// public Collection<Double> minAvgMaxChorbiesAges() {
	// Collection<Double> result;
	//
	// result = chorbiRepository.minAvgMaxChorbiesAges();
	//
	// return result;
	// }

	public Double minChorbiesAges() {
		Double result;

		result = chorbiRepository.minChorbiesAges();

		return result;
	}

	public Double maxChorbiesAges() {
		Double result;

		result = chorbiRepository.maxChorbiesAges();

		return result;
	}

	public Double avgChorbiesAges() {
		Double result;

		result = chorbiRepository.avgChorbiesAges();

		return result;
	}

	public Double ratioChorbiesWithCreditCard() {
		Double result;

		result = chorbiRepository.ratioChorbiesWithCreditCard();

		return result;
	}

	public Double ratioChorbiesWithoutCreditCard() {
		Double result;

		result = chorbiRepository.ratioChorbiesWithoutCreditCard();

		return result;
	}

	public Double ratioChorbiesFindActivities() {
		Double result;

		result = chorbiRepository.ratioChorbiesFindActivities();

		return result;
	}

	public Double ratioChorbiesFindFriendship() {
		Double result;

		result = chorbiRepository.ratioChorbiesFindFriendship();

		return result;
	}

	public Double ratioChorbiesFindLove() {
		Double result;

		result = chorbiRepository.ratioChorbiesFindLove();

		return result;
	}

	public void ban(int chorbiId) {
		Chorbi chorbi = this.findOne(chorbiId);
		Assert.notNull(chorbi);
		if (chorbi.getUserAccount().isEnabled())
			chorbi.getUserAccount().setEnabled(false);
		else
			chorbi.getUserAccount().setEnabled(true);

		this.save(chorbi);

	}

	public int calculaEdad(Date birth) {

		if (birth == null) {
			return 0;
		}

		Calendar birthC = Calendar.getInstance();
		birthC.setTime(birth);

		Calendar today = Calendar.getInstance();
		int diffYear = today.get(Calendar.YEAR) - birthC.get(Calendar.YEAR);
		int diffMonth = today.get(Calendar.MONTH) - birthC.get(Calendar.MONTH);
		int diffDay = today.get(Calendar.DAY_OF_MONTH)
				- birthC.get(Calendar.DAY_OF_MONTH);
		// Si está en ese año pero todavía no los ha cumplido
		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
			diffYear = diffYear - 1;
		}
		return diffYear;
	}

	public Collection<Chorbi> findChorbiesByLikes() {
		Collection<Chorbi> result;

		result = chorbiRepository.findChorbiesByLikes();
		Assert.notNull(result);

		return result;
	}

	public ChorbiEditForm constructForm() {
		ChorbiEditForm res = new ChorbiEditForm();
		Chorbi chorbi = this.findByPrincipal();
		Coordinate coordinate = coordinateService
				.findByChorbiId(chorbi.getId());
		Assert.notNull(chorbi);
		res.setBirth(chorbi.getBirth());
		res.setCity(coordinate.getCity());
		res.setCountry(coordinate.getCountry());
		res.setDescription(chorbi.getDescription());
		res.setEmail(chorbi.getEmail());
		res.setGene(chorbi.getGenre());
		res.setName(chorbi.getName());
		res.setPhone(chorbi.getPhoneNumber());
		res.setPicture(chorbi.getPicture());
		res.setProvince(coordinate.getProvince());
		res.setRelationship(chorbi.getRelationship());
		res.setState(coordinate.getState());
		res.setSurname(chorbi.getSurname());

		return res;
	}

	public Chorbi reconstructEdit(ChorbiEditForm chorbiEditForm,
			BindingResult binding) {
		// TODO Auto-generated method stub
		Chorbi chorbi = this.findByPrincipal();
		Assert.notNull(chorbi);

		chorbi.setName(chorbiEditForm.getName());
		chorbi.setSurname(chorbiEditForm.getSurname());
		chorbi.setEmail(chorbiEditForm.getEmail());
		chorbi.setPhoneNumber(chorbiEditForm.getPhone());

		chorbi.setPicture(chorbiEditForm.getPicture());
		chorbi.setRelationship(chorbiEditForm.getRelationship());
		chorbi.setGenre(chorbiEditForm.getGene());
		chorbi.setDescription(chorbiEditForm.getDescription());
		chorbi.setBirth(chorbiEditForm.getBirth());
		chorbi.setAge(calculaEdad(chorbiEditForm.getBirth()));

		if (calculaEdad(chorbiEditForm.getBirth()) < 18)
			chorbiEditForm.setBirth(null);

		validator.validate(chorbiEditForm, binding);

		return chorbi;
	}

	public Collection<Chorbi> findAllChorbies() {
		Collection<Chorbi> res;
		Authority i = new Authority();
		i.setAuthority("ADMINISTRATOR");
		if (actorService.findByPrincipal().getUserAccount().getAuthorities()
				.contains(i))
			res = this.findAll();
		else
			res = this.findAllNotBaned();
		return res;
	}

	public Collection<Chorbi> findAllChorbiesLiked() {
		Collection<Chorbi> res;
		Authority i = new Authority();
		i.setAuthority("ADMINISTRATOR");
		if (actorService.findByPrincipal().getUserAccount().getAuthorities()
				.contains(i))
			res = this.findAll();
		else
			res = this.findAllNotBanedLiked(actorService.findByPrincipal().getId());
		return res;
	}

	
	private Collection<Chorbi> findAllNotBaned() {
		return chorbiRepository.findAllNotBaned();
	}
	
	private Collection<Chorbi> findAllNotBanedLiked(int chorbiId) {
		return chorbiRepository.findAllNotBanedLiked(chorbiId);
	}

	public Collection<Chorbi> maxChirpsReceived() {
		return chorbiRepository.maxChirpsReceived();
	}

	public Collection<Chorbi> maxChirpsSends() {
		return chorbiRepository.maxChirpsSends();
	}

	public String maskCharacters(String cadenaIntroducida) {
		String cadena;

		cadena = cadenaIntroducida.replaceAll("[(][+][0-9]{2,3}[)]\\b(\\d{9})",
				"***");
		cadena = cadena.replaceAll("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b",
				"***");

		return cadena;
	}

	public void updateFees() {
		Administrator admin = (Administrator) actorService.findByPrincipal();
		Assert.notNull(admin);
		
		Double chorbiFee = configurationSystemService.selectChorbiFee();
		Collection<Chorbi> all = this.findAll();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, -1);
		Date date = cal.getTime();
		for (Chorbi c : all) {
			int aux = 0;
			for (Event e : c.getEvents()) {
				if (e.getMoment().after(date))
					aux++;
			}
			c.setMonthlyFee(aux*chorbiFee);
			this.save(c);
		}
	}
	
	public Collection<Chorbi> chorbiOrderByEvents(){
		Collection<Chorbi> res = chorbiRepository.chorbisOrderByEvents();
		Assert.notEmpty(res);
		return res;
	}
	
}

// if(cadenaIntroducida.matches(".*[(][+][0-9]{2,3}[)][0-9]{9}.*")){
// // cadena = "***";
// cadena = cadenaIntroducida.replaceAll(".*[(][+][0-9]{2,3}[)][0-9]{9}.*",
// "$1*******$2");
// }else{
// cadena = cadenaIntroducida;
// }
