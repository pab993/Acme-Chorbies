package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.CoordinateService;
import services.CreditCardService;
import domain.Chorbi;
import domain.Coordinate;
import domain.CreditCard;
import domain.Like;
import forms.ChorbiEditForm;
import forms.ChorbiForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Services
	// ===============================================================================

	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private CoordinateService coordinateService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	// Constructor
	// ============================================================================

	public ChorbiController() {
		super();
	}

	// Create
	// ====================================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView resul;
		resul = new ModelAndView("chorbi/edit");

		resul.addObject("chorbi", new ChorbiForm());

		return resul;
	}

	@RequestMapping(value = "/editChorbi", method = RequestMethod.GET)
	public ModelAndView editChorbi() {

		ModelAndView resul;
		ChorbiEditForm chorbiEditForm;
		chorbiEditForm = chorbiService.constructForm();
		resul = new ModelAndView("chorbi/editChorbi");

		resul.addObject("chorbiEditForm", chorbiEditForm);

		return resul;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView resul;

		resul = new ModelAndView("chorbi/edit");
		resul.addObject("chorbiForm", new ChorbiForm());

		return resul;
	}

	// Edit
	// ===================================================================================

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(ChorbiForm chorbiForm, BindingResult binding) {

		ModelAndView resul;

		Chorbi chorbi = chorbiService.reconstruct(chorbiForm, binding);

		if (binding.hasErrors())
			resul = createEditModelAndView(chorbiForm);

		else {

			try {

				Chorbi saved = chorbiService.save(chorbi);
				Coordinate coordinate = coordinateService.create(chorbiForm.getCountry(), chorbiForm.getCity(), saved, saved.getSearchTemplate());
				
				coordinateService.save(coordinate);
				resul = new ModelAndView("redirect:/welcome/index.do");

			} catch (Throwable oops) {
				resul = createEditModelAndView(chorbiForm,
						"chorbi.commit.error");
			}

		}

		return resul;
	}

	@RequestMapping(value = "editChorbi", method = RequestMethod.POST, params = "save")
	public ModelAndView editChorbi(ChorbiEditForm chorbiEditForm, BindingResult binding) {

		ModelAndView resul;

		if (binding.hasErrors())
			resul = createEditModelAndView(chorbiEditForm);

		else {

			try {
				
				chorbiService.reconstructEdit(chorbiEditForm, binding);
				resul = new ModelAndView("redirect:/welcome/index.do");

			} catch (Throwable oops) {
				resul = createEditModelAndView(chorbiEditForm, "chorbi.commir.error");
			}

		}
		return resul;
	}

	// Listing
	// ====================================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Collection<Chorbi> chorbiesChanged = new ArrayList<Chorbi>();
		Chorbi principal = chorbiService.findByPrincipal();
		CreditCard creditCard = creditCardService.findByChorbiId(principal.getId());
		
		chorbies = chorbiService.findAllChorbies();
		
		chorbies.remove(principal);

		for(Chorbi b : chorbies){
			String description = b.getDescription();
			description = chorbiService.maskCharacters(description);
			b.setDescription(description);
			chorbiesChanged.add(b);
		}
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbiesChanged);
		result.addObject("creditCard", creditCard);
		result.addObject("principal", principal);
		result.addObject("requestURI", "chorbi/list.do");
		
		
		// Chorbis likes
		
		
		Collection<Like> likesSendedByPrincipal = principal.getLikesSended();
		
		Collection<Chorbi> chorbiLikeReceivedFromPrincipal = new ArrayList<Chorbi>();
		
		for (Like like : likesSendedByPrincipal) {
			chorbiLikeReceivedFromPrincipal.add(like.getChorbiRecipient());
		}
		
		
		result.addObject("chorbiLikeReceivedFromPrincipal", chorbiLikeReceivedFromPrincipal);
		
		return result;
	}
	
	@RequestMapping(value = "/listLikes", method = RequestMethod.GET)
	public ModelAndView listLikes() {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Collection<Chorbi> chorbiesChanged = new ArrayList<Chorbi>();
		Chorbi principal = chorbiService.findByPrincipal();
		CreditCard creditCard = creditCardService.findByChorbiId(principal.getId());
		chorbies = chorbiService.findAllChorbiesLiked();
		
		chorbies.remove(principal);

		for(Chorbi b : chorbies){
			String description = b.getDescription();
			description = chorbiService.maskCharacters(description);
			b.setDescription(description);
			chorbiesChanged.add(b);
		}
		
		result = new ModelAndView("chorbi/listLikes");
		result.addObject("chorbies", chorbiesChanged);
		result.addObject("creditCard", creditCard);
		result.addObject("principal", principal);
		result.addObject("requestURI", "chorbi/listLikes.do");
		
		
		// Chorbis likes
		
		
		Collection<Like> likesSendedByPrincipal = principal.getLikesSended();
		
		Collection<Chorbi> chorbiLikeReceivedFromPrincipal = new ArrayList<Chorbi>();
		
		for (Like like : likesSendedByPrincipal) {
			chorbiLikeReceivedFromPrincipal.add(like.getChorbiRecipient());
		}
		
		
		result.addObject("chorbiLikeReceivedFromPrincipal", chorbiLikeReceivedFromPrincipal);
		
		return result;
	}
	
	@RequestMapping(value = "/adminList", method = RequestMethod.GET)
	public ModelAndView adminList() {
		ModelAndView result;
		Collection<Chorbi> chorbies;
//		Administrator principal = administratorService.findByPrincipal();

		chorbies = chorbiService.findAllChorbies();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chorbi/adminList.do");
		
		
		return result;
	}
	
	@RequestMapping(value = "/managerList", method = RequestMethod.GET)
	public ModelAndView managerList() {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Collection<Chorbi> chorbiesChanged = new ArrayList<Chorbi>();
		chorbies = chorbiService.findAllChorbies();
		
		for(Chorbi b : chorbies){
			String description = b.getDescription();
			description = chorbiService.maskCharacters(description);
			b.setDescription(description);
			chorbiesChanged.add(b);
		}

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbiesChanged);
		result.addObject("requestURI", "chorbi/list.do");
		
		
		return result;
	}
	
	
	// Ancilliary methods

	private ModelAndView createEditModelAndView(ChorbiForm chorbiForm) {

		return createEditModelAndView(chorbiForm, null);
	}

	private ModelAndView createEditModelAndView(ChorbiForm chorbiForm,
			String message) {

		ModelAndView resul = new ModelAndView("chorbi/edit");

		resul.addObject("chorbiForm", chorbiForm);
		resul.addObject("message", message);
		return resul;
	}
	
	

	private ModelAndView createEditModelAndView(ChorbiEditForm chorbiEditForm) {
		return createEditModelAndView(chorbiEditForm, null);
	}

	private ModelAndView createEditModelAndView(ChorbiEditForm chorbiEditForm, String message) {

		ModelAndView resul = new ModelAndView("chorbi/editChorbi");

		resul.addObject("chorbiEditForm", chorbiEditForm);
		resul.addObject("message", message);
		return resul;
	}
	
	
}
