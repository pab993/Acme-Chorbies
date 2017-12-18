package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chirp;
import domain.Chorbi;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ChirpServiceTest extends AbstractTest{

	// The SUT
		// ====================================================
		
		@Autowired
		private ChirpService chirpService;
		
		@Autowired
		private ChorbiService chorbiService;
		
		// Tests
		// ====================================================
		
		/*
		 * Chirp to another chorbi.
		 * 
		 * En este caso de uso se crean y se guardan los chirps que queramos intercambiar con otros chorbies siempre y cuando
		 * nos encontramos autentificados como chorbi.
		 * Para provocar el error, se intenta guardar mediante con un usuario no autentificado o autenticado como admin e incluyendo un recipientId no válido.
		 */
		
		public void createTest(String username, Class<?> expected) {
			Class<?> caught;
			
			caught = null;
			try{
				
				authenticate(username);
				chirpService.create();
				unauthenticate();
				
			}catch (Throwable oops) {
				caught = oops.getClass();
			}
			checkExceptions(expected, caught);
			
		}
		
		public void saveTest(String username, int idRecipient, Class<?> expected) {
			Class<?> caught;
			
			caught = null;
			try{
				
				authenticate(username);

				Chirp chirp = chirpService.create();
				chirp.setText("test");
				chirp.setSubject("subject");
				
				chirp.setActorRecipient(chorbiService.findOne(idRecipient));
				chirpService.save(chirp);
				
				unauthenticate();
				
			}catch (Throwable oops) {
				caught = oops.getClass();
			}
			
			checkExceptions(expected, caught);
		}
		
		/*
		 * Erase any of the chirps that he or she's got or sent, which requires previous confirmation.
		 * 
		 * En este caso de uso se borran el chirp que desee el chorbi que se encuentra 
		 * actualmente autentificado siempre y cuando pertenecezca a dicho actor.
		 * Para provocar el error, se intenta acceder mediante un usuario no autentificado o admin.
		 */
		public void chirpDelete(final String username, final Chirp chirp, final Class<?> expected){
			
			Class<?> caught = null;
			
			try {
				
				authenticate(username);
				chirpService.delete(chirp);
				unauthenticate();
				
			} catch (final Throwable oops) {
				
				caught = oops.getClass();
		
			}	
			checkExceptions(expected, caught);
		}
		

		/*
		 * Browse the list of chirps that he or she's got, and reply any of them.
		 * Browse the list of chirps that he or she's got, and re-send any of them.
		 * 
		 * En este caso de uso se listan los chirps que pertenecen al chorbi que  se encuentra 
		 * actualmente autentificado.
		 * Para provocar el error, se intenta acceder mediante con un usuario no autentificado.
		 */
		public void myListOfChirpsTest(final String username, final Class<?> expected) {
			Class<?> caught = null;

			try {
				this.authenticate(username);
				
				Chorbi chorbi;
				chorbi = chorbiService.findByPrincipal();

				this.chirpService.checkIfChorbi();

				for (final Chorbi c : this.chorbiService.findAll())
					if (c.getUserAccount().getUsername().equals(username))
						chorbi = c;
				
				this.chirpService.findAllByChorbi(chorbi.getId());

				this.unauthenticate();

			} catch (final Throwable oops) {

				caught = oops.getClass();

			}

			this.checkExceptions(expected, caught);
		}
		
		public void sendChirpsTest(final String usernameSender, final Chorbi chorbiRecipient, final String subject, final String text, Date createMoment, final Class<?> expected) {
			Class<?> caught = null;

			try {

				this.authenticate(usernameSender);
				
				Chorbi chorbiSender = chorbiService.findOne(496);
				Chirp chirp = chirpService.create();
				chirp.setText(text);
				chirp.setSubject(subject);
				chirp.setCreateMoment(createMoment);
				
				chirp.setActorRecipient(chorbiRecipient);
				chirp.setActorSender(chorbiSender);
				chirpService.save(chirp);

				this.unauthenticate();

			} catch (final Throwable oops) {

				caught = oops.getClass();

			}

			this.checkExceptions(expected, caught);
		}
		
		/*
		 * Broadcast a chirp to the chorbies who have registered to any of the events that he or she manages.
		 * 
		 * En este caso de uso, es posible enviar un 'broadcast' a todos quienes estén registrados a un evento determinado siempre y cuando
		 * nos encontramos autentificados como manager y dicho evento pertenezca a él.
		 * Para provocar el error, se intenta guardar mediante con un usuario no autentificado o autenticado 
		 * comos usuarios, así como introduciendo valores no válidos.
		 */
		public void sendBroadcastTest(final String usernameSender, final String subject, final String text, final Class<?> expected) {
			Class<?> caught = null;

			try {

				this.authenticate(usernameSender);
				
				Chirp chirp = chirpService.create();
				chirp.setText(text);
				chirp.setSubject(subject);
				
				Collection<Chorbi> chorbiesRecipient = chorbiService.findAllChorbies();
				
				chirpService.saveBroadcast(chorbiesRecipient, chirp);

				this.unauthenticate();

			} catch (final Throwable oops) {

				caught = oops.getClass();

			}

			this.checkExceptions(expected, caught);
		}
		
		
		// Drivers
		// ====================================================
		
		@Test
		public void driverCreateChirp(){
			
			Object testingData1[][] = {
				// Creación de un chirp si estoy autentificado como chorbi -> true
				{
				"chorbi1", null
				},
				// Creación de un chirp si estoy autentificado como manager -> true
				{
				"manager1", null
				},
				// Creación de un chirp si no estoy autentificado -> false
				{
				null, IllegalArgumentException.class
				}
			};
			
			for(int i = 0; i<testingData1.length; i++){createTest((String) testingData1[i][0],
						(Class<?>) testingData1[i][1]);
			}
		}
		
		@Test
		public void driverSaveChirp(){
				
			Object testingData[][] = {
				// Si existe el idRecipient -> true
				{
				"chorbi1", 497, null
				},
				// Si no existe el idRecipient -> false
				{
				"chorbi1", 989 , IllegalArgumentException.class
				},
				{
				// Si no estamos autentificados para guardar un chirp pero el idRecipient existe -> false
				null, 497 , IllegalArgumentException.class
				}
			};
			
			for(int i = 0; i<testingData.length; i++){saveTest((String) testingData[i][0],
				(int) testingData[i][1],(Class<?>) testingData[i][2]);
			}
		}

		
		@Test
		public void driverDeleteChirp() {
			final Object testingData[][] = {
				// Chirp de su chorbi -> true
				{
					"chorbi2" ,chirpService.findOne(506), null
				},
				// Un chirp sin auth -> false
				{
					null, chirpService.findOne(506), IllegalArgumentException.class
				},
				// Se indica un chorbi que no existe -> false
				{
					"chorbi" ,chirpService.findOne(506), IllegalArgumentException.class
				},
				// Se intenta borrar un chirp que no pertenece al chorbi indicado -> false
				{
					"chorbi1", chirpService.findOne(506), IllegalArgumentException.class
				}
			};
			for (int i = 0; i < testingData.length; i++)
				this.chirpDelete((String) testingData[i][0], (Chirp) testingData[i][1], (Class<?>) testingData[i][2]);
		}
		
		@Test
		public void driverMyListOfChirpsTest() {
			final Object testingData[][] = {
				// Mi lista si estoy autentificado como chorbi -> true
				{
					"chorbi3", null
				},
				// Mi lista si no estoy autentificado -> false
				{
					null, IllegalArgumentException.class
				},
			};
			for (int i = 0; i < testingData.length; i++)
				this.myListOfChirpsTest((String) testingData[i][0], (Class<?>) testingData[i][1]);

		}
		
		@Test
		public void driverSendChirpsTest() {
			Date date = new Date(System.currentTimeMillis()-1000);
			final Object testingData[][] = {
				
				// Campos introducidos correctos
				{
					"chorbi1", chorbiService.findOne(497), "Hi m8", "Wasup brah, how u'doing?", date, null
				},
				// Campos introducidos incorrectos, usuario sin autenticar
				{
					null, chorbiService.findOne(497), "Hi m8", "Wasup brah, how u'doing?", date, IllegalArgumentException.class
				},
				// Campos introducidos incorrectos, chorbi Recipient nulo
				{
					"chorbi1", null, "Hi m8", "Wasup brah, how u'doing?", date, IllegalArgumentException.class
				},
				// Título vacío
				{
					"chorbi1", chorbiService.findOne(497), null, "Wasup brah, how u'doing?", date, IllegalArgumentException.class
				}
			};
			for (int i = 0; i < testingData.length; i++)
				this.sendChirpsTest((String) testingData[i][0], (Chorbi) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],(Date) testingData[i][4], (Class<?>) testingData[i][5]);

		}
		
		@Test
		public void driverBroadcastChirpsTest() {
			
			final Object testingData[][] = {
				
				// Campos introducidos correctos
				{
					"chorbi1", "Hi m8", "Wasup brah, how u'doing?", null
				},
				// Campos introducidos incorrectos, usuario sin autenticar
				{
					null, "Hi m8", "Wasup brah, how u'doing?", IllegalArgumentException.class
				},
				// Texto vacío
				{
					"chorbi1", "Hi m8", null, IllegalArgumentException.class
				}
			};
			for (int i = 0; i < testingData.length; i++)
				this.sendBroadcastTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

		}
}
