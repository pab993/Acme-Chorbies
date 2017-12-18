
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DalemRepository;
import domain.Actor;
import domain.Administrator;
import domain.Event;
import domain.Dalem;

@Transactional
@Service
public class DalemService {

	//Repository
	//======================================================================

	@Autowired
	private DalemRepository	dalemRepository;

	@Autowired
	private AdministratorService		administratorService;


	//Services
	//======================================================================

	// Constructor methods
	// =====================================================================

	public DalemService() {
		super();
	}

	//CRUD methods
	//=======================================================================

	public Dalem findOne(int id) {
		Assert.isTrue(dalemRepository.exists(id));

		Dalem dalem = dalemRepository.findOne(id);
		Assert.notNull(dalem);

		return dalem;
	}

	public Collection<Dalem> findAll() {
		Collection<Dalem> result;

		result = dalemRepository.findAll();

		return result;
	}

	//Other bussiness methods
	//=======================================================================

	public Dalem create(Event event) {

		Administrator principal = administratorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isInstanceOf(Administrator.class, principal);
		Assert.notNull(event);

		Dalem dalem = new Dalem();
		
		dalem.setCreateMoment(new Date(System.currentTimeMillis() - 100));
		dalem.setCode(codeGenerator());

		dalem.setAdministrator(principal);
		dalem.setEvent(event);
		dalem.setCancel(false);
		
		return dalem;
	}

	public Dalem save(Dalem dalem) {
		// TODO Auto-generated method stub
		Assert.notNull(dalem);
		Assert.isInstanceOf(Administrator.class, administratorService.findByPrincipal());

		Dalem saved = dalemRepository.save(dalem);

		return saved;
	}

	//Other bussiness methods
	//=======================================================================

	public Dalem findOneByAdministratorAndEvent(int administratorId, int eventId) {
		// TODO Auto-generated method stub

		Assert.notNull(administratorId);
		Assert.notNull(eventId);

		Dalem dalem = dalemRepository.findOneByAdministratorAndEvent(administratorId, eventId);

		Assert.notNull(dalem);

		return dalem;
	}

	public Collection<Dalem> findByEvent(int eventId) {
		Collection<Dalem> dalems = new ArrayList<Dalem>();

		dalems = dalemRepository.findByEvent(eventId);

		return dalems;
	}

	public void cancelDalem(Dalem dalem) {

		Assert.notNull(dalem);
		Assert.isInstanceOf(Administrator.class, administratorService.findByPrincipal());
		Assert.notNull(dalem.getJustification());
		dalem.setCancel(true);
		dalemRepository.save(dalem);

	}

	public String codeGenerator() {
		String result = "";
		String pattern2 = "0123456789";
		Random rnd = new Random();
		int nRnd =  generaNumeroAleatorio(3,4);
		int nRnd2 =  generaNumeroAleatorio(2,5);
		
		for (int i = 0; i < nRnd; i++) {
			result += pattern2.charAt(rnd.nextInt(pattern2.length()));
		}
		result += "--";
		for (int i = 0; i < nRnd2; i++) {
			result += pattern2.charAt(rnd.nextInt(pattern2.length()));
		}
		return result;
	}
	
	
	public static int generaNumeroAleatorio(int minimo,int maximo){
        
	       int num=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));
	       return num;
	   }
	
	
//	public String codeGenerator(){
//		String result = "";
//		String digits = "0123456789";
//		String alphas = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//		Random rnd = new Random();
//		
//		boolean equal = false;
//		do{
//			for(int i=0;i<3;i++){
//				result = result + digits.charAt(rnd.nextInt(digits.length()));
//			}
//			result = result + "-";
//			
//			for(int i=0;i<4;i++){
//				result = result + alphas.charAt(rnd.nextInt(alphas.length()));
//			}
//			Assert.isTrue(result.matches("^[a-zA-Z0-9]{3}-[0-9]{5}$"));
//			
//			Collection<Review> reviews = reviewRepository.findAll();
//			for(Review r:reviews){
//				equal = result.contentEquals(r.getCode());
//				if(equal == true){
//					break;
//				}
//			}
//		}while(equal);
//		
//		return result;
//	}		


	//Reconstruct
	//=======================================================================

	@Autowired
	private Validator	validator;


	public Dalem reconstruct(Dalem dalem, BindingResult binding) {
		// TODO Auto-generated method stub

		Dalem resul;

		if (dalem.getId() == 0)
			resul = dalem;
		else {

			resul = dalemRepository.findOne(dalem.getId());

		}

		validator.validate(resul, binding);

		return resul;
	}

}