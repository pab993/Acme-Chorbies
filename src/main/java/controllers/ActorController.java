package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;


@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController{
	
	@Autowired
	private ActorService actorService;

	
	public ActorController() {
		super();
	}
	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int actorId) {

		Actor actor =  actorService.findOne(actorId);
		ModelAndView resul = new ModelAndView("actor/display");
		resul.addObject("actor", actor);
		resul.addObject("requestURI", "actor/display.do");

		return resul;
	}
}
