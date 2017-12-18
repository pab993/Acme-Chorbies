package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import services.CreditCardService;
import domain.Actor;
import domain.Chorbi;
import domain.CreditCard;
import domain.Event;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController{

	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private ActorService actorService;

	public CreditCardController() {
		super();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard creditCard;
		Actor actor = actorService.findByPrincipal();
		creditCard = creditCardService.create();
		creditCard.setActor(actor);
		result = createEditModelAndView(creditCard);

		return result;
	}
	
	
	@RequestMapping("/edit")
	public ModelAndView edit() {
		CreditCard creditCard;
		ModelAndView result;
		
		Actor actor = actorService.findByPrincipal();
		
		creditCard = creditCardService.findByChorbiId(actor.getId());

		if(creditCard == null){
			result = new ModelAndView("redirect:create.do");
		}else{
		
			result = new ModelAndView("creditCard/edit");
			result.addObject("creditCard", creditCard);
	
		}
		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CreditCard creditCard,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(creditCard);

		} else {
			try {

				creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (Throwable oops) {
				result = createEditModelAndView(creditCard,
						"creditCard.commit.error");
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView result;

		try {
			creditCardService.delete(creditCard);
			result = new ModelAndView("redirect:create.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(creditCard, "creditCard.commit.error");
		}
		return result;
	}
	
	
	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(CreditCard creditCard) {
		ModelAndView result;

		result = createEditModelAndView(creditCard, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(CreditCard creditCard, String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");

		result.addObject("creditCard", creditCard);
		result.addObject("message", message);

		return result;
	}
}
