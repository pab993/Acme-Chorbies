package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SearchTemplateRepository;
import domain.Chorbi;
import domain.SearchTemplate;
import forms.SearchTemplateForm;
import domain.Relationship;
import domain.Genre;

@Transactional
@Service
public class SearchTemplateService {

	//Repository
	//======================================================================
	
	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;
	
	//Services
	//======================================================================
	
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private Validator			validator;
	
	// Constructor methods
	// ====================================================================================

	public SearchTemplateService() {
		super();
	}
	
	//CRUD methods
	//=======================================================================
	
	public SearchTemplate findOne(int id) {
		SearchTemplate result;
		result = searchTemplateRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
	}

	public Collection<SearchTemplate> findAll() {

		Collection<SearchTemplate> searchs;

		searchs = searchTemplateRepository.findAll();
		Assert.notNull(searchs);

		return searchs;
	}

	public SearchTemplate create() {
		
		SearchTemplate result = new SearchTemplate();
		
		Collection<Chorbi> chorbies;
		chorbies = new ArrayList<Chorbi>();
		
		result.setChorbiesWanted(chorbies);
		result.setAge(18);
		
		//Probablemente tenga que hacer más sets

		return result;
	}

	public SearchTemplate save(SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);
		SearchTemplate result;
		Chorbi chorbi;
		
		long l = 10;
		Date fechaActual = new Date(System.currentTimeMillis() - l);
		
		searchTemplate.setLastSearch(fechaActual);
		
		chorbi = chorbiService.findByPrincipal();
		Assert.isTrue(searchTemplate.getId() == chorbi.getSearchTemplate().getId());

		result = searchTemplateRepository.save(searchTemplate);

		return result;
	}
	
	
	//Other bussiness methods
	//=======================================================================
	
	public SearchTemplate reconstruct(SearchTemplateForm searchTemplateForm, BindingResult binding) {
		SearchTemplate result;

		Chorbi chorbi = chorbiService.findByPrincipal();
		result = searchTemplateRepository.findOne(chorbi.getSearchTemplate().getId());

		long l = 10;
		Date actualSearch = new Date(System.currentTimeMillis() - l);

		result.setLastSearch(actualSearch);
		result.setRelationship(searchTemplateForm.getRelationship());
		result.setAge(searchTemplateForm.getAge());
		//Meter aqui la query de la busqueda y añadirlo a chorbiesWanted
		result.setGenre(searchTemplateForm.getGenre());
		result.setKeyword(searchTemplateForm.getKeyword());

		validator.validate(result, binding);
		
		return result;
	}
	
	public Collection<Chorbi> search(String relationship, int age, String genre, String keyword, String city, String country, String state, String province) {
		Collection<Chorbi> chorbies;
		
//		Relationship.valueOf(relationship);
//		Genre.valueOf(genre);
		Assert.isTrue(18 <= age && age <= 100);
		
		chorbiService.findByPrincipal();
		chorbies = searchTemplateRepository.search(relationship, age, genre, keyword, city, country, state, province);

		return chorbies;

	}

	public SearchTemplate save2(SearchTemplate searchTemplate) {
		// TODO Auto-generated method stub
		Assert.notNull(searchTemplate);
		SearchTemplate saved;
		long l = 10;
		Date fechaActual = new Date(System.currentTimeMillis() - l);
		
		searchTemplate.setLastSearch(fechaActual);

		saved = searchTemplateRepository.save(searchTemplate);
		
		return saved;
	}
}
