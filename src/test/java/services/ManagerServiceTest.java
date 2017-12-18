package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Event;
import domain.Manager;

import utilities.AbstractTest;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerServiceTest extends AbstractTest{

	// The SUT
	// ====================================================
		
	@Autowired
	private ManagerService managerService;
		
	// Tests
	// ====================================================
	
	public void managerRegisterTest(final String username, final String password, final String name, final String surname, final String vat, final String company, final String email, final String fee, final String phone, final Class<?> expected) {
		Class<?> caught = null;
		
		Manager manager1 = managerService.create();
		
		manager1.getUserAccount().setUsername(username);
		manager1.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));
		manager1.setCompany(company);
		manager1.setEmail(email);
		Collection<Event> events = new ArrayList<Event>();
		manager1.setEvents(events);
		manager1.setFee(0.0);
		manager1.setName(name);
		manager1.setPhoneNumber(phone);
		manager1.setSurname(surname);
		manager1.setVat(vat);
		
		try {
			
			managerService.save(manager1);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	//Drivers
	// ===================================================
	
	@Test
	public void driverManagerRegister() {
		
		final Object testingData[][] = {
			//  Todos los campos correctos -> true
			{
				"test123", "test123", "testName", "testSurname", "112314013294903397", "Hola S.L.", "test@gmail.com", "0.0", "(+34)651427564", null
			},
			//  Todos los campos menos el username -> false
			{
				"test1234", "test1234", null, "testSurname2", "112314013294903397", "Hola S.L.", "test1@gmail.com", "0.0", "(+34)651427564", ConstraintViolationException.class
			},
			//

		};
		for (int i = 0; i < testingData.length; i++)
			this.managerRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	
}
