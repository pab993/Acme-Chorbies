package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;
import forms.ManagerForm;

@Controller
@RequestMapping("/manag")
public class ManagerController extends AbstractController {

	// Services
	// ===============================================================================

	@Autowired
	private ManagerService managerService;
	
	// Constructor
	// ============================================================================

	public ManagerController() {
		super();
	}

	// Create
	// ====================================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		result = new ModelAndView("manager/edit");
		result.addObject("managerForm", new ManagerForm());

		return result;
	}

	// Edit
	// ===================================================================================

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(ManagerForm managerForm, BindingResult binding) {

		ModelAndView result;

		Manager manager= managerService.reconstruct(managerForm, binding);

		if (binding.hasErrors())
			result = createEditModelAndView(managerForm);

		else {

			try {

				managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (Throwable oops) {
				result = createEditModelAndView(managerForm,
						"manager.commit.error");
			}

		}

		return result;
	}

	

	private ModelAndView createEditModelAndView(ManagerForm managerForm) {

		return createEditModelAndView(managerForm, null);
	}

	private ModelAndView createEditModelAndView(ManagerForm managerForm,
			String message) {

		ModelAndView resul = new ModelAndView("manager/edit");

		resul.addObject("managerForm", managerForm);
		resul.addObject("message", message);
		return resul;
	}
}
