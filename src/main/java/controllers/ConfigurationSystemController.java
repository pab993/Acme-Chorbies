package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.ConfigurationSystem;

import services.ConfigurationSystemService;


@Controller
@RequestMapping("/configurationSystem")
public class ConfigurationSystemController extends AbstractController {

	@Autowired
	private ConfigurationSystemService configurationSystemService;

	public ConfigurationSystemController() {
		super();
	}

	@RequestMapping("/edit")
	public ModelAndView edit() {
		ConfigurationSystem configurationSystem;
		configurationSystem = configurationSystemService.getCS();

		ModelAndView result;

		result = new ModelAndView("configurationSystem/edit");
		result.addObject("configurationSystem", configurationSystem);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ConfigurationSystem configurationSystem,
			BindingResult binding) {
		ModelAndView result;
//		List<FieldError> errores;
//		
//		errores = this.configurationSystemService.comprobarErrores(configurationSystem, "configurationSystem", null);
//		if (errores != null && !errores.isEmpty()) {
//			for (FieldError error : errores) {
//				binding.addError(error);
//			}
//		}

		if (binding.hasErrors()) {
			result = createEditModelAndView(configurationSystem);

		} else {
			try {

				configurationSystemService.save(configurationSystem);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (Throwable oops) {
				result = createEditModelAndView(configurationSystem,
						"configurationSystem.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(ConfigurationSystem configurationSystem) {
		ModelAndView result;

		result = createEditModelAndView(configurationSystem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ConfigurationSystem configurationSystem,
			String message) {
		ModelAndView result;

		result = new ModelAndView("configurationSystem/edit");

		result.addObject("configurationSystem", configurationSystem);
		result.addObject("message", message);

		return result;
	}
}
