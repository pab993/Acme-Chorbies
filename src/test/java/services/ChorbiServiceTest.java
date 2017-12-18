package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chorbi;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ChorbiServiceTest extends AbstractTest{

	// The SUT
	// ====================================================
	
	@Autowired 
	private ChorbiService chorbiService;
	
	@Autowired
	private ActorService actorService;
	
//	@Autowired
//	private SearchTemplateService searchTemplateService;
	
	// Tests
	// ====================================================
	
	
	@Test
	public void driver(){
		
		Chorbi chorbi = chorbiService.create();
		Chorbi chorbi1 = chorbiService.create();
		Chorbi chorbi2 = chorbiService.create();
		Chorbi chorbi3 = chorbiService.create();
		
		chorbi.setEmail("test@hotmail.com");
		chorbi.setName("test");
		chorbi.setSurname("test");
		chorbi.setPhoneNumber("(+34)647382899");
		
		// Creo fecha
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		String strFecha = "11/08/1992";
		Date fecha = null;
		try {
			fecha = formatoDelTexto.parse(strFecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
		chorbi.setBirth(fecha);
		chorbi.setAge(chorbiService.calculaEdad(fecha));
		chorbi.setPicture("http://wwww.picture2.com");
		chorbi.setDescription("Hola  q ase");
		chorbi.setGenre("MAN");
		chorbi.setRelationship("LOVE");
		chorbi.getUserAccount().setUsername("testst");
		chorbi.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword("adadsa", null));
		
		// OTRO CHORBI
		// ============================
		
		
		chorbi1.setName("test");
		chorbi1.setSurname("test");
		chorbi1.setPhoneNumber("(+34)647382899");
		
		chorbi1.setBirth(fecha);
		chorbi1.setAge(chorbiService.calculaEdad(fecha));
		chorbi1.setPicture("http://wwww.picture2.com");
		chorbi1.setDescription("Hola  q ase");
		chorbi1.setGenre("MAN");
		chorbi1.setRelationship("LOVE");
		
		chorbi1.getUserAccount().setUsername("testdsst");
		chorbi1.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword("adadssa", null));
		
		// OTRO CHORBI
				// ============================
				
				chorbi2.setEmail("test1@hotmail.com");
				chorbi2.setName("test");
				
				chorbi2.setPhoneNumber("(+34)647382899");
				
				chorbi2.setBirth(fecha);
				chorbi2.setAge(chorbiService.calculaEdad(fecha));
				chorbi2.setPicture("http://wwww.picture2.com");
				chorbi2.setDescription("");
				chorbi2.setGenre("MAN");
				chorbi2.setRelationship("LOVE");
				
				chorbi2.getUserAccount().setUsername("testdssst");
				chorbi2.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword("adaadsa", null));
				
				// OTRO CHORBI
				// ============================
				
				chorbi3.setEmail("");
				chorbi3.setName("test");
				chorbi3.setSurname("test");
				chorbi3.setPhoneNumber("(+34)647382899");
				
				chorbi3.setBirth(fecha);
				chorbi3.setAge(chorbiService.calculaEdad(fecha));
				chorbi3.setPicture("");
				chorbi3.setDescription("");
				chorbi3.setGenre("MAN");
				chorbi3.setRelationship("LOVE");
				
				chorbi3.getUserAccount().setUsername("ttystuykst");
				chorbi3.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword("ad534akudsa", null));
		
		/*UserAccount userAccount = new UserAccount();	// añado autoridad CUSTOMER a la cuenta de usuario del customer
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority("CHORBI");
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		chorbi.setUserAccount(userAccount);
		
		// Fecha a parsear
		String miFecha = "19900125143025";
		// Objeto Calendar
		Calendar fechaCalendar = Calendar.getInstance();
		fechaCalendar.set(Calendar.YEAR, Integer.valueOf(miFecha.substring(0, 4)));
		fechaCalendar.set(Calendar.MONTH, Integer.valueOf(miFecha.substring(4, 6)) - 1);
		fechaCalendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(miFecha.substring(6, 8)));
		fechaCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(miFecha.substring(8, 10)));
		fechaCalendar.set(Calendar.MINUTE, Integer.valueOf(miFecha.substring(10, 12)));
		fechaCalendar.set(Calendar.SECOND, Integer.valueOf(miFecha.substring(12, 14)));
		
		Date fechaDate = new Date(fechaCalendar.getTimeInMillis());
		
		chorbi.setId(0);
		chorbi.setVersion(0);
		chorbi.setEmail("test@hotmail.com");
		chorbi.setName("test");
		chorbi.setSurname("test");
		chorbi.setPhoneNumber("(+34)647382899");
		chorbi.setBirth(fechaDate);
		chorbi.setPicture("http://wwww.picture2.com");
		chorbi.setDescription("Hola  q ase");
		chorbi.setGenre("MAN");
		SearchTemplate st = new SearchTemplate();
		st.setAge(18);*/
		
		
		
		Object testingData[][] = {
			{chorbi, null},
			{chorbi1, ConstraintViolationException.class},
			{null, IllegalArgumentException.class}
		};
		
		for(int i = 0; i < testingData.length; i++){
			registerTest((Chorbi) testingData[i][0],
				(Class<?>) testingData[i][1]);
		}
		
		testingData = null;
		
		
	}
	
	/*
	 * Browse the list of chorbies who have registered to the system and navigate to the chorbies who like them.
	 * 
	 * En este caso de uso se comprobamos que un chorbi o admin puede listar los chorbies que existen en nuestra aplicación.
	 * Forzaremos el error siempre y cuando no se esté autenticado.
	 */
	
	public void listOfChorbiTest(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			this.chorbiService.findAll();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	
	public void registerTest(Chorbi chorbi, Class<?> expected) {
		Class<?> caught;
		
		caught = null;
		try{
			
			chorbiService.save(chorbi);
		
		}catch (Throwable oops) {
			
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	
	@Test
	public void maskCharacters(){
		
		String cadena = " Este el mi número de telefono (+34)654321234 y aquí tienes mi correo chorbi@gmail.com ";
		String otracadena = "hola senor@gmail.com";
		String nuevaCadena = chorbiService.maskCharacters(cadena);
		String newcadena = chorbiService.maskCharacters(otracadena);
		
		System.out.println(cadena);
		System.out.println("---------------------------------------------");
		System.out.println(nuevaCadena);
		System.out.println(newcadena);
		
	}
	
	
	
}
