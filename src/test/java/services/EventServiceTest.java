package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Event;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest extends AbstractTest{

	// The SUT
	// ====================================================
		
	@Autowired
	private EventService eventService;
		
	// Tests
	// ====================================================
	/*
	 * Manage the events that he or she organises, which includes listing, registering, 
	 * mod-ifying, and deleting them. In order to register a new event, he must have registered 
	 * a valid credit card that must not expire in less than one day. Every time he or she 
	 * registers an event, the system will simulate that he or she's charged a 1.00 fee.
	 * 
	 * En este caso de uso se crean, editan, se lista y se guardan los eventos que queramos siempre y cuando
	 * nos encontramos autentificados como manager.
	 * Para provocar el error, se intenta guardar mediante con un usuario no autentificado o autenticado 
	 * comos usuarios, así como introduciendo valores no válidos.
	 */
	
	

	/*
	 * Register to an event as long there are enough seats available.
	 * Un-register from an event to which he or she's registered.
	 * 
	 * En este caso de uso, los chorbies pueden registrarse o desregistrarse de los eventos que quieran o pertenezcan siempre y cuando
	 * nos encontramos autentificados como chorbi.
	 * Para provocar el error, se intenta guardar mediante con un usuario no autentificado o autenticado 
	 * comos usuarios, así como introduciendo valores no válidos.
	 */
	public void eventSaveTest(final String username, final String title, final String description, final Date date, final String picture, final int seats, final Class<?> expected) {
		Class<?> caught = null;
		
		Event event = new Event();
		event.setTitle(title);
		event.setDescription(description);
		event.setMoment(date);
		event.setPicture(picture);
		event.setSeatsNumber(seats);
		Collection<Chorbi> chorbies = new ArrayList<Chorbi>();
		event.setChorbies(chorbies);
		
		try {
			authenticate(username);
			
			eventService.saveCreate(event);
			
			unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	public void eventEditTest(final String username, final Event event, final Class<?> expected) {
		Class<?> caught = null;
		
		try {
			authenticate(username);
			
			eventService.save(event);
			
			unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	public void eventDeleteTest(final String username, final Event event, final Class<?> expected) {
		Class<?> caught = null;
		
		try {
			authenticate(username);
			
			eventService.delete(event);
			
			unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	
	//Drivers
	// ===================================================
	
	@Test
	public void driverEventSaveTest() {
		
		long l = 100000;
		Date futureDate = new Date(System.currentTimeMillis() + l);

		Date pastDate = new Date(System.currentTimeMillis() - l);
		
		final Object testingData[][] = {
			//  Todos los campos correctos -> true
			{
				"manager2", "evento34", "This is a event de la parra neng", futureDate, "http://www.imagen.com.mx/assets/img/imagen_share.png", 25, null
			},
			//  Registrar un evento con una credit card inválida /nula -> false
			{
				"manager1", "evento34", "This is a event de la parra neng", futureDate, "http://www.imagen.com.mx/assets/img/imagen_share.png", 25, NullPointerException.class
			},
			//  Registrar un evento con una fecha pasada-> false
			{
				"manager2", "evento34", "This is a event de la parra neng", pastDate, "http://www.imagen.com.mx/assets/img/imagen_share.png", 25, IllegalArgumentException.class
			},
			//  un número de sitios inválidos-> false
			{
				"manager2", "evento34", "This is a event de la parra neng", pastDate, "http://www.imagen.com.mx/assets/img/imagen_share.png", -5, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.eventSaveTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	
	@Test
	public void driverEventEditTest() {
		
		final Object testingData[][] = {
			//  Todos los campos correctos -> true
			{
				"manager1", eventService.findOne(484), null
			},
			//Editar un evento sin estar logueado -> false
			{
				null, eventService.findOne(484), IllegalArgumentException.class
			},
			//Editar un evento sin ser tuyo -> false
			{
				"manager1", eventService.findOne(485), IllegalArgumentException.class
			}
	
		};
		for (int i = 0; i < testingData.length; i++)
			this.eventEditTest((String) testingData[i][0], (Event) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverEventDeleteTest() {
		
		final Object testingData[][] = {
			//  Todos los campos correctos -> true
			{
				"manager3", eventService.findOne(485), null
			},
			//Borrar un evento sin estar logueado -> false
			{
				null, eventService.findOne(485), IllegalArgumentException.class
			},
			//Borrar un evento sin ser tuyo -> false
			{
				"manager1", eventService.findOne(485), IllegalArgumentException.class
			}
	
		};
		for (int i = 0; i < testingData.length; i++)
			this.eventDeleteTest((String) testingData[i][0], (Event) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverEventRegisterTest() {
		
		final Object testingData[][] = {
			{"chorbi1", 484, null},								//Apuntarse a un evento
			{null, 484, IllegalArgumentException.class},		//Apuntarse sin estar logueado
			{"chorbi2", 484, IllegalArgumentException.class},	//Apuntarse a un evento otra vez
			{"chorbi1", 485, IllegalArgumentException.class},	//Auntarse a un evento que ya sucedió
			{"chorbi2", 487, IllegalArgumentException.class}};	//Apuntarse a un evento sin asientos
	
	for(int i = 0; i < testingData.length; i++){
		evenRegisterTest((String)testingData[i][0],(int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}

	protected void evenRegisterTest(String username, int idEvent, Class<?> expected) {
		Class<?> caught = null;
		
		try {
			
			authenticate(username);
			
			Event eventTest = eventService.findOne(idEvent);
			eventService.register(eventTest);
			
			authenticate(null);
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
		
	}
	
	
	@Test
	public void driverEventUnRegisterTest() {
		
		final Object testingData[][] = {
			{"chorbi2", 484, null},
			{null, 484, IllegalArgumentException.class},	//sin login
			{"chorbi2", 483, IllegalArgumentException.class},	//chorbi se eliminade un evento al que no esta inscrito
			{"chorbi1", 486, IllegalArgumentException.class} 	//chorbi se elimina de un evento al que ya asistió
		};
		
		for(int i = 0; i< testingData.length; i++){
			eventUnRegisterTest((String) testingData[i][0],(int) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}

	protected void eventUnRegisterTest(String uername, int id, Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?>caught = null;
		
		try {
			
			authenticate(uername);
			
			Event eventToTest = eventService.findOne(id);
			eventService.unRegister(eventToTest);
			
			authenticate(null);
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}
	
	@Test
	public void driverEventFindAllPrincipalTest() {
		
		final Object testData[][] = {
			{"chorbi1", 496, null},
			{null, 496, IllegalArgumentException.class},
			{"chorbi2", 496, IllegalArgumentException.class}
		};
		
		for(int i = 0; i<testData.length; i++){
			eventFindAllPrincipal((String) testData[i][0], (int) testData[i][1], (Class<?>)testData[i][2]);
		}
	}

	protected void eventFindAllPrincipal(String username, int id, Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught = null;
		
		try {
			
			authenticate(username);
			
			eventService.findAllRegistered(id);
			
			authenticate(null);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}

}
