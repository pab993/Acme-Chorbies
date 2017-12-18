package controllers.chorbi;

import java.util.Collection;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.ChorbiService;
import services.EventService;
import domain.Chorbi;
import domain.Event;

@Service
@RequestMapping("/event/chorbi/")
public class ChorbiEventController extends AbstractController{

	@Autowired
	private EventService eventService;
	
	@Autowired
	private ChorbiService chorbiService;
	
	public ChorbiEventController(){
		super();
	}
	
	//
	// LISTING REGISTERED EVENTS
	
	@RequestMapping(value="myEvents", method=RequestMethod.GET)
	public ModelAndView listEventsRegistered(){
		
		ModelAndView resul = new ModelAndView("event/list");
		
		Chorbi principal = chorbiService.findByPrincipal();
		Collection<Event> events = principal.getEvents();
		Date fecha = new Date(System.currentTimeMillis());
		
		resul.addObject("events", events);
		resul.addObject("principal", principal);
		resul.addObject("fecha", fecha);
		
		resul.addObject("requestURI", "event/chorbi/list.do");
		return resul;
	}
	
	// REGISTERING =====================================================================
	
	@RequestMapping(value="register", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int idEvent){
		
		ModelAndView resul;
		resul = new ModelAndView("redirect:/event/chorbi/myEvents.do");
		
		try{
		
			Event event = eventService.findOne(idEvent);
			eventService.register(event);		// agrego al eventos
			
		}catch (Throwable oops) {
			
			resul.addObject("message", "error.commit.seats");
		}
		return resul;
	}
	
	@RequestMapping(value="unRegister", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int idEvent){
		
		ModelAndView resul;
		
		Event event  = eventService.findOne(idEvent);
		eventService.unRegister(event);		//elimino del evento
		
		resul = new ModelAndView("redirect:/event/chorbi/myEvents.do");
		
		return resul;
	}
	
	
	// Listing
	// ====================================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;
		Chorbi principal = chorbiService.findByPrincipal();
		events = eventService.findAll();
		
		result = new ModelAndView("event/list");
		
		Date fecha = new Date(System.currentTimeMillis());
		
		result.addObject("principal", principal);
		result.addObject("events", events);
		result.addObject("fecha", fecha);
		result.addObject("requestURI", "event/chorbi/list.do");
		return result;
	}
}
