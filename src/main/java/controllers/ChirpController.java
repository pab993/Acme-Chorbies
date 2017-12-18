
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import services.ChorbiService;
import services.EventService;
import services.ManagerService;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import forms.ChirpForm;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	//Services ===============================================================================

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ChorbiService	chorbiService;
	
	@Autowired
	private ManagerService	managerService;
	
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private EventService	eventService;


	//Constructor ============================================================================

	public ChirpController() {
		super();
	}

	// Listing
	// ====================================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> chirps;
		Collection<Chirp> chirpsChanged = new ArrayList<Chirp>();
		Actor actor;
		int principalId;
		
		actor = actorService.findByPrincipal();
		chirps = chirpService.findAllByChorbi(actor.getId());
		principalId = actor.getId();
		
		for(Chirp c : chirps){
			String subject = c.getSubject();
			String text = c.getText();
			subject = chorbiService.maskCharacters(subject);
			text = chorbiService.maskCharacters(text);
			c.setSubject(subject);
			c.setText(text);
			chirpsChanged.add(c);
		}

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirpsChanged);
		result.addObject("principalId", principalId);
		result.addObject("requestURI", "chirp/list.do");

		return result;
	}

	//Creation 
	// ====================================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ChirpForm chirpForm;

		chirpForm = new ChirpForm();

		result = createEditModelAndView(chirpForm);

		return result;
	}

	//Edit 
	// ====================================================================================

	@RequestMapping(value = "/editReply", method = RequestMethod.GET)
	public ModelAndView editReply(@RequestParam int chirpId) {
		ModelAndView result;
		Chirp chirp;
		Chirp chirpReply;

		chirp = chirpService.findOne(chirpId);
		chirpReply = chirpService.create();
		
		chirpReply.setId(chirp.getId());
		
		result = createEditModelAndViewReply(chirpReply);

		return result;
	}
	
	@RequestMapping(value = "/editForward", method = RequestMethod.GET)
	public ModelAndView editForward(@RequestParam int chirpId) {
		ModelAndView result;
		Chirp chirp;
		Chirp chirpForward;

		chirp = chirpService.findOne(chirpId);
		chirpForward = chirpService.create();
		
		chirpForward.setId(chirp.getId());
		
		result = createEditModelAndViewForward(chirpForward);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ChirpForm chirpForm, BindingResult binding) {
		ModelAndView result;

		Chirp chirpRec = chirpService.reconstruct(chirpForm, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(chirpForm);
		} else {
			try {
				chirpService.save(chirpRec);
				result = new ModelAndView("redirect:/chirp/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(chirpForm, "message.commit.error");
			}
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/editReply", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReply(Chirp chirp, BindingResult binding) {
		ModelAndView result;
		Date createMoment;
		
		createMoment = new Date(System.currentTimeMillis());
		Actor actorReply = chirpService.findOne(chirp.getId()).getActorSender();
		chirp.setActorSender(actorService.findByPrincipal());
		chirp.setActorRecipient((Chorbi)actorReply);
		chirp.setId(0);
		chirp.setCreateMoment(createMoment);

		if (binding.hasErrors()) {
			result = createEditModelAndViewReply(chirp);
		} else {
			try {
				chirpService.save(chirp);
				result = new ModelAndView("redirect:/chirp/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndViewReply(chirp, "message.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/editForward", method = RequestMethod.POST, params = "save")
	public ModelAndView saveForward(Chirp chirp, BindingResult binding) {
		ModelAndView result;
		Date createMoment;
		
		createMoment = new Date(System.currentTimeMillis());
		Chirp chirpForward = chirpService.findOne(chirp.getId());
		chirp.setActorSender(chorbiService.findByPrincipal());
		chirp.setSubject(chirpForward.getSubject());
		chirp.setText(chirpForward.getText());
		chirp.setAttachments(chirpForward.getAttachments());
		chirp.setId(0);
		chirp.setCreateMoment(createMoment);

		if (binding.hasErrors()) {
			result = createEditModelAndViewReply(chirp);
		} else {
			try {
				chirpService.save(chirp);
				result = new ModelAndView("redirect:/chirp/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndViewReply(chirp, "message.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/editBroadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBroadcast(Chirp chirp, BindingResult binding) {
		ModelAndView result;
		Event eventBroadcast = eventService.findOne(chirp.getId());
		Collection<Chorbi> chorbiesBroadcast = new ArrayList<Chorbi>();
		Date createMoment;
		createMoment = new Date(System.currentTimeMillis());
		chorbiesBroadcast.addAll(eventBroadcast.getChorbies());
		
		chirp.setActorSender(managerService.findByPrincipal());
		chirp.setId(0);
		chirp.setCreateMoment(createMoment);

		if (binding.hasErrors()) {
			result = createEditModelAndViewBroadcast(chirp);
		} else {
			try {
				for(Chorbi c : chorbiesBroadcast){
					chirp.setActorRecipient(c);
					chirpService.save(chirp);
				}
				
				result = new ModelAndView("redirect:/manager/events/myEvents.do");
			} catch (Throwable oops) {
				result = createEditModelAndViewBroadcast(chirp, "event.commit.error");
			}
		}
		return result;
	}

	//Delete 
	// ====================================================================================
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int chirpId) {
		ModelAndView result;
		Chirp chirpDelete;
		
		
		chirpDelete = chirpService.findOne(chirpId);

		result = new ModelAndView("chirp/delete");
		result.addObject("chirpDelete", chirpDelete);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Chirp chirp, BindingResult binding) {
		ModelAndView result;

		try {
			chirpService.delete(chirp);
			result = new ModelAndView("redirect:/chirp/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/chirp/list.do");
		}
		return result;
	}
	
	// The ancillary methods 
	// ====================================================================================

	protected ModelAndView createEditModelAndView(ChirpForm chirpSend) {
		ModelAndView result;

		result = createEditModelAndView(chirpSend, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ChirpForm chirpSend, String message) {
		ModelAndView result;
		Collection<Chorbi> chorbiRecipients;

		result = new ModelAndView("chirp/edit");
		chorbiRecipients = chorbiService.findAll();

		result.addObject("chirpForm", chirpSend);
		result.addObject("chorbiRecipients", chorbiRecipients);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndViewReply(Chirp chirpReply) {
		ModelAndView result;

		result = createEditModelAndViewReply(chirpReply, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewReply(Chirp chirpReply, String message) {
		ModelAndView result;

		result = new ModelAndView("chirp/editReply");

		result.addObject("chirpReply", chirpReply);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewForward(Chirp chirpForward) {
		ModelAndView result;

		result = createEditModelAndViewForward(chirpForward, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewForward(Chirp chirpForward, String message) {
		ModelAndView result;
		Collection<Chorbi> chorbiRecipients;		

		result = new ModelAndView("chirp/editForward");
		chorbiRecipients = chorbiService.findAll();

		result.addObject("chirpForward", chirpForward);
		result.addObject("chorbiRecipients", chorbiRecipients);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndViewBroadcast(Chirp chirpBroadcast) {
		ModelAndView result;

		result = createEditModelAndViewBroadcast(chirpBroadcast, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewBroadcast(Chirp chirpBroadcast, String message) {
		ModelAndView result;

		result = new ModelAndView("chirp/editBroadcast");

		result.addObject("chirpBroadcast", chirpBroadcast);
		result.addObject("message", message);

		return result;
	}
}
