package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.ConfigurationSystem;
import domain.CreditCard;
import domain.Event;
import domain.Manager;

@Transactional
@Service
public class EventService {

	//Repository
	//======================================================================
	
	@Autowired
	private EventRepository	eventRepository;
	
	//Services
	// ======================================================================
	
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ConfigurationSystemService configurationSystemService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ChirpService chirpService;
	
	@Autowired
	private Validator validator;
	
	//CRUD methods
	//=======================================================================
	
	public Event findOne(int id){
		Event event;
		
		event = eventRepository.findOne(id);
		Assert.notNull(event);
		
		return event;
	}
	
	
	public Event create(){
		Event e = new Event();
		Assert.isInstanceOf(Manager.class, actorService.findByPrincipal());
		return e;
	}
	
	public Collection<Event> findAll() {

		Collection<Event> events;

		events = eventRepository.findAll();
		Assert.notNull(events);

		return events;
	}
	
	
	public Event save(Event event) {
		Assert.notNull(event);
		Event result;
		Manager manager;
		
		manager = managerService.findByPrincipal();

		Assert.isTrue(manager.getEvents().contains(event));
		
		long l = 20;
		Date actual = new Date(System.currentTimeMillis() - l);
		Calendar actualDate = Calendar.getInstance();
		actualDate.setTime(actual);
		
		Date last = event.getMoment();
		Calendar lastMoment = Calendar.getInstance();
		lastMoment.setTime(last);
		
		long millisEvent = lastMoment.getTimeInMillis();
		long millisActual = actualDate.getTimeInMillis();
		long diff = millisEvent - millisActual;
		
		Assert.isTrue(diff > 0, "Evento Pasado");
		Assert.isTrue(event.getChorbies().size() <= event.getSeatsNumber(), "Mas chorbies que sitios");
		
		result = eventRepository.save(event);

		return result;
	}
	
//	public Event saveAndChirp(Event event, Collection<Chorbi> chorbies) {
//		Assert.notNull(event);
//		Event result;
//		Manager manager;
//		
//		manager = managerService.findByPrincipal();
//
//		Assert.isTrue(manager.getEvents().contains(event));
//		
//		long l = 20;
//		Date actual = new Date(System.currentTimeMillis() - l);
//		Calendar actualDate = Calendar.getInstance();
//		actualDate.setTime(actual);
//		
//		Date last = event.getMoment();
//		Calendar lastMoment = Calendar.getInstance();
//		lastMoment.setTime(last);
//		
//		long millisEvent = lastMoment.getTimeInMillis();
//		long millisActual = actualDate.getTimeInMillis();
//		long diff = millisEvent - millisActual;
//		
//		Assert.isTrue(diff > 0, "Evento Pasado");
//		Assert.isTrue(event.getChorbies().size() <= event.getSeatsNumber(), "Mas chorbies que sitios");
//		
//		result = eventRepository.save(event);
//		
//		chirpService.saveBroadcast(chorbies);
//
//		return result;
//	}
	
	public Event saveCreate(Event event) {
		Assert.notNull(event);
		Event result;
		Manager manager;
		
		long l = 10;
		Date actual = new Date(System.currentTimeMillis() - l);
		Calendar actualDate = Calendar.getInstance();
		actualDate.setTime(actual);
		
		Date last = event.getMoment();
		Calendar lastMoment = Calendar.getInstance();
		lastMoment.setTime(last);
		
		long millisEvent = lastMoment.getTimeInMillis();
		long millisActual = actualDate.getTimeInMillis();
		long diff = millisEvent - millisActual;
		
		Assert.isTrue(diff > 0, "Fecha pasada");
		
		ConfigurationSystem cs = configurationSystemService.getCS();
		manager = managerService.findByPrincipal();
		CreditCard creditCard = creditCardService.findByManagerId(manager.getId());
		Assert.isTrue(creditCardService.checkValidity(creditCard));
		
		manager.setFee(manager.getFee() + cs.getManagerFee());
		event.setManager(manager);
		result = eventRepository.save(event);
		manager.getEvents().add(result);
		managerService.save(manager);

		return result;
	}
	
	public void delete(Event event){
		
		Assert.notNull(event);
		Manager manager = managerService.findByPrincipal();
		Assert.isTrue(eventRepository.exists(event.getId()));
		Assert.isTrue(manager.getEvents().contains(event));
		manager.getEvents().remove(event);
		
		eventRepository.delete(event);
		
	}


	public Event register(Event event) {
		
		
		Chorbi principal = chorbiService.findByPrincipal();
		
		check(event);
		
		event.getChorbies().add(principal);
		principal.getEvents().add(event);
		
		Event saved  = eventRepository.save(event);
		chorbiService.save(principal);
		
		return saved;
	}
	
	public Event unRegister(Event event) {
		// TODO Auto-generated method stub
		
		Chorbi principal = chorbiService.findByPrincipal();
		
		check2(event);
		
		event.getChorbies().remove(principal);
		principal.getEvents().remove(event);
				
		Event saved  = eventRepository.save(event);
		chorbiService.save(principal);
		
		return saved;
	}


	private void check2(Event event) {
		// TODO Auto-generated method stub
		Assert.isTrue(event.getChorbies().contains(chorbiService.findByPrincipal())); // chorbi no esta registrado en el evento
		Date fechaActual = new Date(System.currentTimeMillis());
		Assert.isTrue(event.getMoment().after(fechaActual)); 	// Si el chorbi ya asistió, no se puede desregistrar del mismo
	}


	private void check(Event event) {
		// TODO Auto-generated method stub
		
		Assert.isTrue(event.getChorbies().size() < event.getSeatsNumber());	// asientos suficientes
		Assert.isTrue(!event.getChorbies().contains(chorbiService.findByPrincipal())); // chorbi no esta registrado en el evento
		
		Date fechaActual = new Date(System.currentTimeMillis());
		Assert.isTrue(fechaActual.before(event.getMoment()), "evento acabó");
	}

	//Other Methods
	//=======================================================================


	public Collection<Event> findAllRegistered(int id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		
		Assert.isTrue(chorbiService.findByPrincipal().getId() == id);
		
		Collection<Event> events = eventRepository.findAllRegistered(id);
		
		return events;
	}

	public Collection<Event> findEventsByManager(int idManager){
		Assert.notNull(idManager);
		
		Collection<Event> events = eventRepository.findEventsByManager(idManager);
		
		Assert.notNull(events);
		return events;
		
	}
	
	public Collection<Event> findByMonthToStartAndSeats() {	
		Collection<Event> results;
		Calendar currentCalendar;
		Calendar currentPlusOneMonth;

		currentCalendar = Calendar.getInstance();
		currentCalendar.add(Calendar.MILLISECOND, -10);

		currentPlusOneMonth = Calendar.getInstance();
		currentPlusOneMonth.add(Calendar.MONTH, 1);

		results = this.eventRepository.findByMonthToStartAndSeats(currentCalendar.getTime(), currentPlusOneMonth.getTime());

		return results;
	}
	
	public Collection<Event> findByPastsEvents() {
		return eventRepository.findByPastEvents();
	}

	public Collection<Event> findFutureEvents() {
		Collection<Event> results;
		Calendar currentPlusOneMonth;

		currentPlusOneMonth = Calendar.getInstance();
		currentPlusOneMonth.add(Calendar.MONTH, 1);

		results = this.eventRepository.findFutureEvents(currentPlusOneMonth.getTime());

		return results;
	}
	
	public Event reconstruct(Event event, BindingResult binding){
		Event result;
		
		if(event.getId() == 0){
			result = event;
		}else{
			result = eventRepository.findOne(event.getId());
			result.setTitle(event.getTitle());
			result.setDescription(event.getDescription());
			result.setMoment(event.getMoment());
			result.setPicture(event.getPicture());
			result.setSeatsNumber(event.getSeatsNumber());
			
			validator.validate(result, binding);
		}
		
		return result;
	}

}
