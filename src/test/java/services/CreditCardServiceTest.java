package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CreditCardServiceTest extends AbstractTest{

	// The SUT
	// ====================================================
		
	@Autowired
	private CreditCardService creditCardService;
		
	// Tests
	// ====================================================
	
	/*
	 * To check the validity of a credit card, the system must check its brand (which must be either VISA, MASTERCARD, 
	 * DISCOVER, DINNERS, or AMEX), its number (which must pass Luhn's check), and its expiration date 
	 * (which is expected To be at least one day ahead).
	 * 
	 * En este test, se comprueba si el número introducido de la tarjeta pasa el test de verificación de Luhn.
	 * Para ello introducimos un número correcto que no devolverá ninguna excepción y a continuación introduciremos un número incorrecto
	 * que no pasará el test y por tanto saltará un error esperado.
	 */
	
	public void verificacionLuhnTest(final String username, final CreditCard creditCard, final Class<?> expected){
		
		Class<?> caught = null;

		try{
			
			this.authenticate(username);
	
			int[] digits = creditCardService.stringToArray(creditCard.getNumber());
			Assert.isTrue(creditCardService.verificacionLuhn(digits));
			
			this.unauthenticate();
			
		}catch(Throwable oops){
			
			caught = oops.getClass();
			
		}
		
		this.checkExceptions(expected, caught);
		
	}
	
	/*
	 * En este test, se comprueba si el brand name de la tarjeta es válido o no.
	 * Para ello introducimos un brand name correcto y a continuación introduciremos brand name incorrecto
	 * que no pasará el test y por tanto saltará un error esperado.
	 
	 */
	
	public void checkBrandNameTest(final String username, final CreditCard creditCard, final Class<?> expected){
		
		Class<?> caught = null;

		try{
			
			this.authenticate(username);
	
			boolean res = creditCardService.checkBrandName(creditCard);
			Assert.isTrue(res);
			
			this.unauthenticate();
			
		}catch(Throwable oops){
			
			caught = oops.getClass();
			
		}
		
		this.checkExceptions(expected, caught);
		
	}
	
	/*
	 * En este test, se comprueba si la fecha de la tarjeta es válida o no.
	 * Para ello introducimos una fecha que caduca en el futuro y a continuación introduciremos otra fecha
	 * que no pasará el test por estar caducada y por tanto saltará un error esperado.
	 
	 */
	
	public void checkExpirationDateTest(final String username, final CreditCard creditCard, final Class<?> expected){
		
		Class<?> caught = null;

		try{
			
			this.authenticate(username);
	
			boolean res = creditCardService.checkExpirationDate(creditCard);
			Assert.isTrue(res);
			
			this.unauthenticate();
			
		}catch(Throwable oops){
			
			caught = oops.getClass();
			
		}
		
		this.checkExceptions(expected, caught);
		
	}
	
	//Drivers
	// ===================================================
	
	@Test
	public void driverVerificacionLuhnTest() {
		CreditCard creditCard = creditCardService.findOne(508);
		CreditCard creditCard2 = creditCardService.findOne(509);
		creditCard2.setNumber("123456789");
		
		final Object testingData[][] = {
			// Una creditCard con un número válido -> true
			{
				"chorbi1", creditCard, null
			},
			// Una creditCart con un número no válido -> false
			{
				"chorbi1", creditCard2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.verificacionLuhnTest((String) testingData[i][0], (CreditCard) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	
	@Test
	public void driverCheckBrandNameTest() {
		CreditCard creditCard = creditCardService.findOne(508);
		CreditCard creditCard2 = creditCardService.findOne(509);
		creditCard2.setBrandName("No válido");
		
		final Object testingData[][] = {
			// Un brandName válido -> true
			{
				"chorbi1", creditCard, null
			},
			// Una creditCart con un número no válido -> false
			{
				"chorbi1", creditCard2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.checkBrandNameTest((String) testingData[i][0], (CreditCard) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverCheckExpirationDateTest() {
		CreditCard creditCard = creditCardService.findOne(508);
		CreditCard creditCard2 = creditCardService.findOne(509);
		
		Date expireDate = new GregorianCalendar(2010, Calendar.FEBRUARY, 11).getTime();
		Calendar expireCalendarDate = Calendar.getInstance();
		expireCalendarDate.setTime(expireDate);
		creditCard2.setExpirationYear(expireCalendarDate.YEAR);
		
		final Object testingData[][] = {
			// Una creditCard con una fecha válida -> true
			{
				"chorbi1", creditCard, null
			},
			// Una creditCard con una fecha inválida -> false
			{
				"chorbi1", creditCard2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.checkExpirationDateTest((String) testingData[i][0], (CreditCard) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
}
