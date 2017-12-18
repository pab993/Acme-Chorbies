package controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.EventService;
import domain.Actor;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController{
	
	// Services
	// ===============================================================================

	@Autowired
	private EventService eventService;
	
	@Autowired
	private ActorService actorService;

	
	// Constructor
	// ============================================================================

	public EventController() {
		super();
	}

//	@RequestMapping("/list")
//	public ModelAndView list() {
//		ModelAndView result;
//
//		Collection<Event> events;
//		Collection<Event> eventsPast;
//		Collection<Event> eventsFuture;
//
//		events = eventService.findByMonthToStartAndSeats();
//		eventsPast = eventService.findByPastsEvents();
//		eventsFuture = this.eventService.findFutureEvents();
//
//		result = new ModelAndView("event/listNotAuth");
//
//		result.addObject("events", events);
//		result.addObject("eventsPast", eventsPast);
//		result.addObject("eventsFuture", eventsFuture);
//		result.addObject("requestURI", "event/list.do");
//		
//		return result;
//	}
	
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;

		Collection<Event> events;
		Date date = new Date(System.currentTimeMillis());
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		
		Actor principal = actorService.findByPrincipal();
		Date fecha = new Date(System.currentTimeMillis());
		
		events = eventService.findAll();

		result = new ModelAndView("event/listNotAuth");

		result.addObject("events", events);
		result.addObject("currentDate", currentDate.getTimeInMillis());
		result.addObject("principal", principal);
		result.addObject("fecha", fecha);
		result.addObject("requestURI", "event/list.do");
		
		return result;
	}
	
	
	@RequestMapping("/lessOneMonth")
	public ModelAndView upComingEventsList() {
		ModelAndView result;

		Collection<Event> events;

		events = eventService.findByMonthToStartAndSeats();

		result = new ModelAndView("event/listFuture");

		result.addObject("eventsFuture", events);
		result.addObject("requestURI", "event/lessOneMonth.do");
		
		return result;
	}

}
