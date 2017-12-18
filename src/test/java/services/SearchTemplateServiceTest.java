package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Chorbi;
import domain.SearchTemplate;

import utilities.AbstractTest;


@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class SearchTemplateServiceTest extends AbstractTest{

	// The SUT
	// ====================================================
	
	@Autowired
	private SearchTemplateService searchTemplateService;
	
	// Tests
	// ====================================================
	
	/*
	 * En este caso de uso simplemente probamos a buscar un searchTemplate pasandole una id de un searchTemplate,
	 * otra id = 0, la cual fallará, y por último la id de algo que no es una searchTemplate y por tanto también fallará.
	 
	 */

	public void findOneTest(final int id, final Class<?> expected){
		
		Class<?> caught = null;

		try{
			SearchTemplate result;
			result = searchTemplateService.findOne(id);
	
			System.out.println("Test findOne: " + result.getId());
	
			System.out.println("----------------------------------------");
		}catch(Throwable oops){
			
			caught = oops.getClass();
			
		}
		
		this.checkExceptions(expected, caught);
		
	}
	
	
	/*
	 * En esta prueba simplemente comprobamos que devuelve todos los searchTemplates con éxito.
	 
	 */
	
	@Test
	public void findAllTest() {

		Collection<SearchTemplate> result = new ArrayList<SearchTemplate>();

		result = this.searchTemplateService.findAll();

		System.out.println("Test findAll: Encontrados " + result.size() + " searchsTemplates");

		System.out.println("----------------------------------------");
	}
	
	/* Browse the results of his or her search term as a valid credit card. 
	 * Note that the validity of the credit card must be checked every time 
	 * the results of the search template are displayed. The results of search-plates 
	 * must be cached for at least 12 hours.
	 * 
	 * En este caso de uso intentamos realizar diferentes búsquedas de chorbies mediante la entidad searchTemplate, 
	 * Para ello hemos introducido diversos valores explicados más detalladamente en el driverSearchTemplateTest, 
	 * como por ejemplo intentar realizar una búsqueda con todos los requisitos en orden o intentar realizar una búsqueda 
	 * sin estar logueado o realizar una búsqueda sin tener una tarjeta de crédito valida, etc...
	 
	 */
	
	public void searchTemplateTest(final String username, final String relationship, final int age, final String genre, final String keyword, final String city, final String country, final String state, final String province, final Class<?> expected) {
		Class<?> caught = null;
		try {

			this.authenticate(username);

			Collection<Chorbi> chorbies;
			chorbies = searchTemplateService.search(relationship, age, genre, keyword, city, country, state, province);
			Assert.notNull(chorbies);
			System.out.println("Test número de chorbies encontrados: " + chorbies.size());
			
			System.out.println("----------------------------------------");

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/* Change his or her search template.
	 * 
	 * En este caso de uso intentamos guardar un searchTemplate del chorbi que esta registrado y luego intentamos guardar
	 * un searchTemplate que no nos pertenece o simplemente intentar guardar un searchTemplate sin estar logueado.
	 
	 */
	
	public void searchTemplateSaveTest(final String username, final SearchTemplate searchTemplate, final Class<?> expected){
		
		Class<?> caught = null;
		
		try {
			
			authenticate(username);
			searchTemplateService.save(searchTemplate);
			unauthenticate();
			
		} catch (final Throwable oops) {
			
			caught = oops.getClass();
	
		}
	
		this.checkExceptions(expected, caught);
	}
	
	
	//Drivers
	// ===================================================
	
	@Test
	public void driverSearchTemplateFindOne() {
		final Object testingData[][] = {
			// Id de un searchTemplate -> true
			{
				488, null
			},
			// Un searchTemplate con id 0 -> false
			{
				0, IllegalArgumentException.class
			},
			// Una id que no es de una searchTemplate -> false
			{
				8, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.findOneTest((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	@Test
	public void driverSearchTemplateTest() {
		
		final Object testingData[][] = {
			// Si estamos autentificado como chorbi1 y con todos los campos rellenos -> true
			{
				"chorbi1", "FRIENDSHIP", 23, "MAN", "guiño, guiño", "Madrid", "España", "Estado de masachuches", "Comunidad de Madrid", null
			},
			// Si estamos autentificado como chorbi1 y sin ninguna coordinate rellena -> false
			{
				"chorbi1", "ACTIVITIES", 22, "WOMAN", "", "", "", "", "", null
			},
			// Si no estamos autentificado -> false
			{
				null, "ACTIVITIES", 22, "WOMAN", "", "", "", "", "", IllegalArgumentException.class
			},
			// Si introducimos un valor no aceptable en age -> false
			{
				"chorbi1", "LOVE", 122, "WOMAN", "", "", "España", "", "", IllegalArgumentException.class
			},
			// Si introducimos un valor no aceptable en age -> false
			{
				"chorbi1", "LOVE", 101, "WOMAN", "", "", "España", "", "", IllegalArgumentException.class
			},
			//Si introducimos la edad limite 100 en age -> true
			{
				"chorbi1", "LOVE", 100, "WOMAN", "", "", "España", "", "", null
			},
			//Si introducimos la edad minima en age -> true
			{
				"chorbi1", "LOVE", 18, "WOMAN", "", "", "España", "", "", null
			},
			//Si introducimos menos de la edad minima en age -> false
			{
				"chorbi1", "LOVE", 17, "WOMAN", "", "", "España", "", "", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.searchTemplateTest((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	
	@Test
	public void driverSearchTemplateSaveTest() {
		final Object testingData[][] = {
			// searchTemplate de su chorbi -> true
			{
				"chorbi1" ,searchTemplateService.findOne(488), null
			},
			// searchTemplate de otro chorbi que no es su searchTemplate -> false
			{
				"chorbi1" ,searchTemplateService.findOne(489), IllegalArgumentException.class
			},
			// intentar guardar un searchTemplate sin estar logueado -> false
			{
				null, searchTemplateService.findOne(488), IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.searchTemplateSaveTest((String) testingData[i][0], (SearchTemplate) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
}
