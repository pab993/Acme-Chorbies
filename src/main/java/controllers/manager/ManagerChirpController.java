
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.EventService;
import controllers.AbstractController;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;

@Service
@RequestMapping("/manag/events")
public class ManagerChirpController extends AbstractController {

	//Services ===============================================================================

	@Autowired
	private ChirpService	chirpService;
	
	@Autowired
	private EventService	eventService;


	//Constructor ============================================================================

	public ManagerChirpController() {
		super();
	}

	//Broadcast ============================================================================

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast(@RequestParam int eventId) {
		ModelAndView result;
		Chirp chirpBroadcast;

		chirpBroadcast = chirpService.create();

		result = createEditModelAndViewBroadcast(chirpBroadcast, eventId);

		return result;
	}

	@RequestMapping(value = "/editBroadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBroadcast(@RequestParam int eventId, Chirp chirp, BindingResult binding) {
		ModelAndView result;
		Event eventBroadcast = eventService.findOne(eventId);
		Collection<Chorbi> chorbiesBroadcast = new ArrayList<Chorbi>();
				
		chorbiesBroadcast.addAll(eventBroadcast.getChorbies());

		if (binding.hasErrors()) {
			result = createEditModelAndViewBroadcast(chirp, eventId);
		} else {
			try {
				chirpService.saveBroadcast(chorbiesBroadcast, chirp);

				result = new ModelAndView("redirect:/manag/events/myEvents.do");
				
			} catch (Throwable oops) {
				result = createEditModelAndViewBroadcast(chirp, eventId, "event.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndViewBroadcast(Chirp chirpBroadcast, int eventId) {
		ModelAndView result;

		result = createEditModelAndViewBroadcast(chirpBroadcast, eventId, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewBroadcast(Chirp chirpBroadcast, int eventId, String message) {
		ModelAndView result;

		result = new ModelAndView("event/editBroadcast");

		result.addObject("requestURI", "manag/events/editBroadcast.do?eventId=" + eventId);
		result.addObject("chirpBroadcast", chirpBroadcast);
		result.addObject("message", message);

		return result;
	}

}
