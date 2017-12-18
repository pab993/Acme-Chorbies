package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.ConfigurationSystemService;
import services.CreditCardService;
import services.SearchTemplateService;

import domain.Chorbi;
import domain.ConfigurationSystem;
import domain.CreditCard;
import domain.Genre;
import domain.Relationship;
import domain.SearchTemplate;
import forms.SearchTemplateForm;


@Controller
@RequestMapping("/searchTemplate")
public class SearchTemplateController extends AbstractController{
	
	//Services
	//=======================================================
	
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private SearchTemplateService searchTemplateService;
	
	@Autowired
	private ConfigurationSystemService configurationSystemService;
	

	// Constructors
	//=======================================================

	public SearchTemplateController() {
		super();
	}
	
	// Listing
	// ====================================================================================

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = null;
		Collection<Chorbi> chorbies;

		Chorbi chorbi = chorbiService.findByPrincipal();
		SearchTemplate searchTemplate = chorbi.getSearchTemplate();
		CreditCard creditCard = creditCardService.findByChorbiId(chorbi.getId());
//		Coordinate coordinate = coordinateService.findByChorbiId(chorbi.getId());
		
		if(creditCard == null || !creditCardService.checkValidity(creditCard)){
			result = new ModelAndView("searchTemplate/invalidCreditCard");
			result.addObject("requestURI", "searchTemplate/invalidCreditCard.do");
			return result;
		}else{
			
			Date last = chorbi.getSearchTemplate().getLastSearch();
			Calendar lastSearch = Calendar.getInstance();
			lastSearch.setTime(last);
	
			long l = 10;
			Date actual = new Date(System.currentTimeMillis() - l);
			Calendar actualDate = Calendar.getInstance();
			actualDate.setTime(actual);
			
			long diff = actualDate.getTimeInMillis() - lastSearch.getTimeInMillis();
			
			ConfigurationSystem cs = configurationSystemService.getCS();
			
//			int x = cs.getHoursTemplate() * cs.getMinutesTemplate() * cs.getSecondsTemplate() * 1000;
	
//			if (actualDate.get(Calendar.YEAR) > lastSearch.get(Calendar.YEAR)) {
//				searchTemplate.setChorbiesWanted(null);
//				searchTemplate = searchTemplateService.save(searchTemplate);
//			} else if (actualDate.get(Calendar.YEAR) == lastSearch.get(Calendar.YEAR) && actualDate.get(Calendar.MONTH) + 1 > lastSearch.get(Calendar.MONTH) + 1) {
//				searchTemplate.setChorbiesWanted(null);
//				searchTemplate = searchTemplateService.save(searchTemplate);
//			} else if (actualDate.get(Calendar.YEAR) == lastSearch.get(Calendar.YEAR) && actualDate.get(Calendar.MONTH) + 1 == lastSearch.get(Calendar.MONTH) + 1 && actualDate.get(Calendar.DAY_OF_MONTH) > lastSearch.get(Calendar.DAY_OF_MONTH)) {
//				searchTemplate.setChorbiesWanted(null);
//				searchTemplate = searchTemplateService.save(searchTemplate);
//			} else if (actualDate.get(Calendar.YEAR) == lastSearch.get(Calendar.YEAR) && actualDate.get(Calendar.MONTH) + 1 == lastSearch.get(Calendar.MONTH) + 1 && actualDate.get(Calendar.DAY_OF_MONTH) > lastSearch.get(Calendar.DAY_OF_MONTH) && (diff > 12 * 60 * 60 * 1000)) {
//				searchTemplate.setChorbiesWanted(null);
//				searchTemplate = searchTemplateService.save(searchTemplate);
//			}
			
			if(cs.getSecondsTemplate() == 0 && cs.getMinutesTemplate() == 0 && cs.getHoursTemplate() == 0){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}else if(cs.getSecondsTemplate() == 0 && cs.getMinutesTemplate() != 0 && cs.getHoursTemplate() !=0 && diff > cs.getHoursTemplate() * 60 * cs.getMinutesTemplate() * 60 * 1000){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}else if(cs.getSecondsTemplate() == 0 && cs.getMinutesTemplate() == 0 && cs.getHoursTemplate() !=0 && diff > cs.getHoursTemplate() * 60 * 60 * 1000){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}else if(cs.getSecondsTemplate() != 0 && cs.getMinutesTemplate() == 0 && cs.getHoursTemplate() !=0 && diff > cs.getMinutesTemplate() * 60 * 1000){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}else if(cs.getSecondsTemplate() != 0 && cs.getMinutesTemplate() == 0 && cs.getHoursTemplate() ==0 && diff > cs.getSecondsTemplate() * 1000){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}else if(cs.getSecondsTemplate() != 0 && cs.getMinutesTemplate() != 0 && cs.getHoursTemplate() ==0 && diff > cs.getMinutesTemplate() * 60 * cs.getSecondsTemplate() * 1000){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}else if(cs.getSecondsTemplate() != 0 && cs.getMinutesTemplate() != 0 && cs.getHoursTemplate() !=0 && diff > cs.getHoursTemplate() * 60 * cs.getMinutesTemplate() * 60 * cs.getSecondsTemplate() * 1000){
				searchTemplate.setChorbiesWanted(null);
				searchTemplate = searchTemplateService.save(searchTemplate);
			}
			
			chorbies = searchTemplate.getChorbiesWanted();
			SearchTemplateForm searchTemplateForm = new SearchTemplateForm();
			
			searchTemplateForm.setAge(searchTemplate.getAge());
			searchTemplateForm.setGenre(searchTemplate.getGenre());
			searchTemplateForm.setKeyword(searchTemplate.getKeyword());
//			Collection<String> relationships = new ArrayList<String>();
//			Collection<String> genres = new ArrayList<String>();
			Collection<Relationship> relationships = Arrays.asList(Relationship.values());
			Collection<Genre> genres = Arrays.asList(Genre.values());
			Collection<Integer> ages = new ArrayList<Integer>();
//			relationships.add(Relationship.ACTIVITIES.toString());
//			relationships.add(Relationship.FRIENDSHIP.toString());
//			relationships.add(Relationship.LOVE.toString());
//			genres.add(Genre.MAN.toString());
//			genres.add(Genre.WOMAN.toString());
			int i;
			for(i = 18; i <= 100; i++){
				ages.add(i);
			}
			
			
			result = new ModelAndView("searchTemplate/display");
			result.addObject("searchTemplateForm", searchTemplateForm);
			result.addObject("chorbies", chorbies);
			result.addObject("ages", ages);
			result.addObject("genres", genres);
			result.addObject("relationships", relationships);
			result.addObject("requestURI", "searchTemplate/display.do");
	
			return result;
		}
	}
	
	@RequestMapping(value = "/invalidCreditCard", method = RequestMethod.GET)
	public ModelAndView invalidCreditCard() {
		ModelAndView result;

		result = new ModelAndView("searchTemplate/invalidCreditCard");
	
		return result;
	}
	
	//Search
	// ====================================================================================

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(SearchTemplateForm searchTemplateForm, BindingResult binding) {
		ModelAndView result;
		Collection<Chorbi> chorbies;

		if (binding.hasErrors()) {
			result = createEditModelAndView(searchTemplateForm);
		} else {
			try {
				SearchTemplate searchTemplate = searchTemplateService.reconstruct(searchTemplateForm, binding); //Añadir el date aquí
				chorbies = searchTemplateService.search(searchTemplate.getRelationship(), searchTemplate.getAge(), searchTemplate.getGenre(), searchTemplate.getKeyword(), searchTemplateForm.getCity(), searchTemplateForm.getCountry(), searchTemplateForm.getState(), searchTemplateForm.getProvince());
				searchTemplate.setChorbiesWanted(chorbies);
				searchTemplateService.save(searchTemplate);
				result = new ModelAndView("searchTemplate/display");
//				Collection<String> relationships = new ArrayList<String>();
//				Collection<String> genres = new ArrayList<String>();
				Collection<Relationship> relationships = Arrays.asList(Relationship.values());
				Collection<Genre> genres = Arrays.asList(Genre.values());
				Collection<Integer> ages = new ArrayList<Integer>();
//				relationships.add(Relationship.ACTIVITIES.toString());
//				relationships.add(Relationship.FRIENDSHIP.toString());
//				relationships.add(Relationship.LOVE.toString());
//				genres.add(Genre.MAN.toString());
//				genres.add(Genre.WOMAN.toString());
				int i;
				for(i = 18; i <= 100; i++){
					ages.add(i);
				}
				result.addObject("chorbies", chorbies);
				result.addObject("ages", ages);
				result.addObject("genres", genres);
				result.addObject("relationships", relationships);
				result.addObject("requestURI", "searchTemplate/display.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(searchTemplateForm, "searchTemplateForm.commit.error");
			}
		}

		return result;
	}
	
	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(SearchTemplateForm searchTemplateForm) {
		ModelAndView result;

		result = createEditModelAndView(searchTemplateForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(SearchTemplateForm searchTemplateForm, String message) {
		ModelAndView result;

		result = new ModelAndView("searchTemplate/display");
//		Collection<String> relationships = new ArrayList<String>();
//		Collection<String> genres = new ArrayList<String>();
		Collection<Relationship> relationships = Arrays.asList(Relationship.values());
		Collection<Genre> genres = Arrays.asList(Genre.values());
		Collection<Integer> ages = new ArrayList<Integer>();
//		relationships.add(Relationship.ACTIVITIES.toString());
//		relationships.add(Relationship.FRIENDSHIP.toString());
//		relationships.add(Relationship.LOVE.toString());
//		genres.add(Genre.MAN.toString());
//		genres.add(Genre.WOMAN.toString());
		int i;
		for(i = 18; i <= 100; i++){
			ages.add(i);
		}
		result.addObject("ages", ages);
		result.addObject("genres", genres);
		result.addObject("relationships", relationships);
		result.addObject("searchTemplateForm", searchTemplateForm);
		result.addObject("message", message);

		return result;
	}
	
}
