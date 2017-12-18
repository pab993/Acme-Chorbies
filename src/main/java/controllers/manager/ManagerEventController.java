package controllers.manager;

import java.util.Calendar;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import services.ConfigurationSystemService;
import services.CreditCardService;
import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.ConfigurationSystem;
import domain.CreditCard;
import domain.Event;
import domain.Manager;

@Service
@RequestMapping("/manag/events")
public class ManagerEventController extends AbstractController{
	
	// Services
	// ===============================================================================
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ManagerService managerService;
	
	
	@Autowired
	private ChirpService chirpService;
	
	@Autowired
	private ConfigurationSystemService configurationSystemService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	// Constructor
	// ============================================================================

	public ManagerEventController() {
		super();
	}

	// Listing
	// ====================================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;
		Calendar currentDate = Calendar.getInstance();
		Calendar oneMonthDate = Calendar.getInstance();
		currentDate.add(Calendar.MILLISECOND, -10);
		oneMonthDate.add(Calendar.MONTH, 1);
		
		events = eventService.findAll();
		
		ConfigurationSystem cs = configurationSystemService.getCS();
		Manager manager = managerService.findByPrincipal();
		
		result = new ModelAndView("event/list");
		result.addObject("csFee", cs.getManagerFee());
		result.addObject("events", events);
		result.addObject("currentDate", currentDate.getTime());
		result.addObject("oneMonthDate", oneMonthDate.getTime());
		result.addObject("requestURI", "manag/events/list.do");
		result.addObject("principal", manager);
		return result;
	}
	
	@RequestMapping(value = "/myEvents", method = RequestMethod.GET)
	public ModelAndView myEvents() {
		ModelAndView result;
		Collection<Event> events;
		Calendar currentDate = Calendar.getInstance();
		Calendar oneMonthDate = Calendar.getInstance();
		currentDate.add(Calendar.MILLISECOND, -10);
		oneMonthDate.add(Calendar.MONTH, 1);
		
		
		Manager manager = managerService.findByPrincipal();
		
		events = eventService.findEventsByManager(manager.getId());
		
		result = new ModelAndView("event/list");
		result.addObject("principal", manager);
		result.addObject("events", events);
		result.addObject("currentDate", currentDate.getTime());
		result.addObject("oneMonthDate", oneMonthDate.getTime());
		result.addObject("requestURI", "manag/events/myEvents.do");
		return result;
	}
	
	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Event event;
		event = eventService.create();
		result = createEditModelAndView(event);

		return result;
	}
	
	//Editing
	// ===============================================================================
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int eventId) {
		ModelAndView result;
		Event event;

		event = eventService.findOne(eventId);
		
		result = createEditModelAndView(event);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Event eventPruned, BindingResult binding) {
		ModelAndView result;
		Manager manager = managerService.findByPrincipal();
		CreditCard creditCard = creditCardService.findByManagerId(manager.getId());
		
		Event event = eventService.reconstruct(eventPruned, binding);
		
		Collection<Chorbi> chorbies = event.getChorbies();

		if (binding.hasErrors()) {
			result = createEditModelAndView(event);
		} else {
			try {
				if(event.getId() != 0){
					if(!(chorbies.isEmpty())){
						chirpService.saveBroadcast(chorbies);	
					}
					eventService.save(event);
					result = new ModelAndView("redirect:/manag/events/myEvents.do");
				}else if(event.getId() == 0 && creditCard != null && creditCardService.checkValidity(creditCard)){
					eventService.saveCreate(event);
					result = new ModelAndView("redirect:/manag/events/myEvents.do");
				}else{
					result = createEditModelAndView(event, "event.commit.error.creditCard");
				}
				
			} catch (Throwable oops) {
				
				if(oops.getMessage() == "Fecha pasada"){
					result = createEditModelAndView(event, "event.past.date");
				}
				if(oops.getMessage() == "Mas chorbies que sitios"){
					result = createEditModelAndView(event, "event.exceed.places");
				
				}
				if(oops.getMessage() == "Evento Pasado"){
					result = createEditModelAndView(event, "event.date.past");
				
				}else{
					result = createEditModelAndView(event, "event.commit.error");
				}
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Event event, BindingResult binding) {
		ModelAndView result;
		Event eventChorbies = eventService.findOne(event.getId());
		Collection<Chorbi> chorbies = eventChorbies.getChorbies();
		
		try {
			if(!(chorbies.isEmpty())){
				chirpService.saveBroadcast(chorbies);	
			}
			eventService.delete(event);
			result = new ModelAndView("redirect:/manag/events/myEvents.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(event, "event.commit.error");
		}
		return result;
	}
	
	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(Event event) {
		ModelAndView result;

		result = createEditModelAndView(event, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Event event, String message) {
		ModelAndView result;

		result = new ModelAndView("event/edit");
		
		ConfigurationSystem cs = configurationSystemService.getCS();

		result.addObject("csFee", cs.getManagerFee());
		result.addObject("event", event);
		result.addObject("message", message);

		return result;
	}
	
}
