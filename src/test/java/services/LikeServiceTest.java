package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import controllers.chorbi.ChorbiLikeController;

import utilities.AbstractTest;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class LikeServiceTest extends AbstractTest{
	
	// The SUT
	// ====================================================
	
	@Autowired
	private ChorbiLikeController chorbiLikeController;
	
	// Tests
	// ====================================================

	/*
	 * Like another chorbi; To be able to be able to:.
	 * 
	 * En este caso de uso se comprobamos que un chorbi puede darle a me gusta a otro chorbi
	 * siempre y cuando esté registrado como dicho rol. En caso contrario, no será posible.
	 * Por otro lado, vamos a forzar el error si un chorbi le da a la opción de me gusta así mismo
	 * o si el chorbi introducido no existe.
	 */
	
	protected void testLike(String username, int id, Class<?> expected) {
		Class<?> caught;
		
		caught = null;
		try {
			
			authenticate(username);
			chorbiLikeController.create(id);
			authenticate(null);
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}

	@Test
	public void driver(){
		
		Object testingData2[][] = {
			{"chorbi1", 497, null  },
			{"chorbi1", 5, IllegalArgumentException.class}, 	//chorbi no existe
			{"chorbi1", 496, IllegalArgumentException.class},	//like a sí mismo
			{null, 497, IllegalArgumentException.class},		//sin regisrar
			{null, 3, IllegalArgumentException.class}
		};
	
			for(int i = 0; i < testingData2.length; i++){
				testLike((String)testingData2[i][0],
						(int)testingData2[i][1],
						(Class<?>)testingData2[i][2]);
			}
		}
}
