package services;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Chorbi;
import domain.CreditCard;
import domain.BrandNameCreditCard;

import repositories.CreditCardRepository;

@Transactional
@Service
public class CreditCardService {

	//Repository
	//======================================================================
	
	@Autowired
	private CreditCardRepository	creditCardRepository;
	
	//Services
	// ======================================================================
	
	@Autowired
	private ActorService actorService;
	
	//CRUD methods
	//=======================================================================
	
	public CreditCard findOne(int id){
		CreditCard creditCard;
		
		creditCard = creditCardRepository.findOne(id);
		Assert.notNull(creditCard);
		
		return creditCard;
	}
	
	public CreditCard create(){
			
		CreditCard cc = new CreditCard();
		return cc;
		
	}
	
	public CreditCard save(CreditCard c){
		Assert.notNull(c);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(c.getActor().getId() == actor.getId());
		Assert.isTrue(checkValidity(c));
//		delete(c);
		CreditCard creditCardRes = creditCardRepository.save(c);
		return creditCardRes;
	}
	
	public void delete(CreditCard c){
		Assert.notNull(c);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(c.getActor().getId() == actor.getId());
		Assert.isTrue(c.getId()!= 0);
		Assert.isTrue(creditCardRepository.exists(c.getId()));
		creditCardRepository.delete(c);
	}
	
	
	//Other bussiness methods
	//=======================================================================
	
	public int[] stringToArray(String number) {
		char[] a = number.toCharArray();
		int[] n = new int[a.length];

		for (int i = 0; i < a.length; i++) {
			int x = Character.getNumericValue(a[i]);
			n[i] = x;
		}
		return n;
	}
	public boolean verificacionLuhn(int[] digits) {
		int sum = 0;
		int length = digits.length;
		for (int i = 0; i < length; i++) {
			// sacar los digitos en orden inverso
			int digit = digits[length - i - 1];

			// cada segundo número se multiplica por 2
			if (i % 2 == 1) {
				digit = digit * 2;
			}
			if (digit > 9) {
				digit = digit - 9;
			}
			sum = sum + digit;
		}
		return sum % 10 == 0;
	}
	
	public CreditCard findByChorbiId(int chorbiId){
		CreditCard creditCard;
		Assert.notNull(chorbiId);
		
		creditCard = creditCardRepository.findByChorbiId(chorbiId);
	
		return creditCard;
		
	}
	
	public CreditCard findByManagerId(int managerId){
		CreditCard creditCard;
		Assert.notNull(managerId);
		
		creditCard = creditCardRepository.findByManagerId(managerId);
	
		return creditCard;
		
	}
	
	public boolean checkValidity(CreditCard creditCard){
		
		boolean res = false;
		int[] n = stringToArray(creditCard.getNumber());
		
		if(checkBrandName(creditCard) && verificacionLuhn(n) && checkExpirationDate(creditCard)){
			res = true;
		}
		return res;
	}
	
	
	public boolean checkBrandName(CreditCard creditCard){
		
		boolean res = false;
		
		for (BrandNameCreditCard bn : BrandNameCreditCard.values()) {
	        if (bn.name().equals(creditCard.getBrandName())) {
	            res = true;
	        }
	    }
		return res;
	}
	
	public boolean checkExpirationDate(CreditCard creditCard){
		boolean res = true;
		
		long l = 10;
		Calendar actualCalendar = Calendar.getInstance();
		Date actual = new Date(System.currentTimeMillis() - l);
		actualCalendar.setTime(actual);
		
		if(creditCard.getExpirationYear() < actualCalendar.get(Calendar.YEAR)){
			res = false;
		}else if(creditCard.getExpirationYear() >= actualCalendar.get(Calendar.YEAR) && creditCard.getExpirationMonth() < actualCalendar.get(Calendar.MONTH)){
			res = false;
		}
		
		return res;
	}

}
