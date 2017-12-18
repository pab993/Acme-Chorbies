package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.EventService;
import services.DalemService;
import domain.Actor;
import domain.Administrator;
import domain.Chorbi;
import domain.Manager;
import domain.Event;
import domain.Dalem;

@Controller
@RequestMapping("/dalem")
public class DalemController extends AbstractController{
	
	// Services
	// ===============================================================================

	@Autowired
	private DalemService dalemService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActorService actorService;

	
	// Constructor
	// ============================================================================

	public DalemController() {
		super();
	}

	// Listing
		// ============================================================================

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Actor principal = actorService.findByPrincipal();
//		Collection<Authority> myAuth = principal.getUserAccount().getAuthorities();
//		
//		Collection<Authority> auts = new ArrayList<Authority>();
//		Authority au = new Authority();
//		Authority au2 = new Authority();
//		au.setAuthority("CHORBI");
//		au2.setAuthority("MANAGER");
//		auts.add(au);
//		auts.add(au2);
//		
		Assert.notNull(principal);
		
		Date currentMoment;
		currentMoment = new Date(System.currentTimeMillis());
		
		Collection<Dalem> dalems;

		dalems = dalemService.findAll();

		result = new ModelAndView("dalem/list");

		result.addObject("dalems", dalems);
		result.addObject("currentMoment", currentMoment);
		result.addObject("principal", principal);
//		result.addObject("auts", auts);
//		result.addObject("myAuth", myAuth);
		
		result.addObject("requestURI", "dalem/list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/listByEvent", method = RequestMethod.GET)
	public ModelAndView listByEvent(@RequestParam int eventId) {
		ModelAndView result;

		Collection<Dalem> dalems;
		
		Actor principal = actorService.findByPrincipal();
		Boolean actorVar = false;
		if(principal instanceof Chorbi || principal instanceof Manager || principal == null){
			actorVar = true;
		}
		
		Date currentMoment;
		currentMoment = new Date(System.currentTimeMillis());
		
		dalems = dalemService.findByEvent(eventId);
		
		result = new ModelAndView("dalem/list");

		result.addObject("dalems", dalems);
		result.addObject("currentMoment", currentMoment);
		result.addObject("principal", principal);
		result.addObject("actorVar", actorVar);
		result.addObject("requestURI", "dalem/list.do");
		
		return result;
	}
	
	//Creating
		// ===========================================================================

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int eventId) {
			ModelAndView result;
			Dalem dalem;
			Event event = eventService.findOne(eventId);
			
			dalem = dalemService.create(event);
			
			result = createEditModelAndView(dalem);

			return result;
		}
		
		//Editing
		// ===============================================================================
		
		@RequestMapping(value = "/editCancel", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int dalemId) {
			ModelAndView result;
			Dalem dalem;

			dalem = dalemService.findOne(dalemId);
			
			result = createCancelEditModelAndView(dalem);

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Dalem dalem, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors())
				result = createEditModelAndView(dalem);

			else {

				try {

					dalemService.save(dalem);
					result = new ModelAndView("redirect:/welcome/index.do");

				} catch (Throwable oops) {
					result = createEditModelAndView(dalem,
							"dalem.commit.error");
				}

			}

			return result;
		}
		
		@RequestMapping(value = "/editCancel", method = RequestMethod.POST, params = "save")
		public ModelAndView saveCancel(Dalem dalem, BindingResult binding) {
			ModelAndView result;
			
			dalem.setAdministrator(administratorService.findByDalem(dalem));
			
			if (binding.hasErrors())
				result = createCancelEditModelAndView(dalem);

			else {

				try {

					dalemService.cancelDalem(dalem);
					result = new ModelAndView("redirect:/dalem/list.do");

				} catch (Throwable oops) {
					result = createCancelEditModelAndView(dalem,
							"dalem.commit.error");
				}

			}

			return result;
		}
	
		
		// Ancillary Methods
		// ===============================================================================

		protected ModelAndView createEditModelAndView(Dalem dalem) {
			ModelAndView result;

			result = createEditModelAndView(dalem, null);
			return result;
		}

		protected ModelAndView createEditModelAndView(Dalem dalem, String message) {
			ModelAndView result;

			result = new ModelAndView("dalem/edit");
			
			result.addObject("dalem", dalem);
			result.addObject("message", message);

			return result;
		}
		
		protected ModelAndView createCancelEditModelAndView(Dalem dalem) {
			ModelAndView result;

			result = createCancelEditModelAndView(dalem, null);
			return result;
		}

		protected ModelAndView createCancelEditModelAndView(Dalem dalem, String message) {
			ModelAndView result;

			result = new ModelAndView("dalem/editCancel");
			
			result.addObject("dalem", dalem);
			result.addObject("message", message);

			return result;
		}
	
}