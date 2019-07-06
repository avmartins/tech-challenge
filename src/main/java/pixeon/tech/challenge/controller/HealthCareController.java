package pixeon.tech.challenge.controller;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pixeon.tech.challenge.entity.HealthCareInstitution;
import pixeon.tech.challenge.service.HealthCareInstitutionService;

@RestController
@RequestMapping("/healthcares")
public class HealthCareController {

	private static final Logger logger = Logger.getLogger(HealthCareController.class);
	private final HealthCareInstitutionService healthCareInstitutionService;

	public HealthCareController(HealthCareInstitutionService healthCareInstitutionService) {
		this.healthCareInstitutionService = healthCareInstitutionService;
	}

	@GetMapping("list")
	public ModelAndView list(ModelMap modelMap) {
		modelMap.addAttribute("healthCares", healthCareInstitutionService.findAll());
		return new ModelAndView("/fragments/healthcares/list", modelMap);
	}

	@GetMapping("/new")
	public ModelAndView newHealthCare(ModelMap modelMap) {
		modelMap.addAttribute("healthCare", new HealthCareInstitution());
		return new ModelAndView("/fragments/healthcares/new", modelMap);
	}

	@PostMapping("/save")
	public ModelAndView saveHealthCare(
			@ModelAttribute("healthCareInstitution") HealthCareInstitution healthCareInstitution) {

		try {
			healthCareInstitutionService.save(healthCareInstitution);
		} catch (Exception e) {
			logger.info("Erro ao incluir a Instituição de Saúde");
		}

		return new ModelAndView("redirect:/healthcares/list");
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		HealthCareInstitution healthCareInstitution = healthCareInstitutionService.findById(id);
		model.addAttribute("healthCare", healthCareInstitution);
		return new ModelAndView("fragments/healthcares/edit", model);
	}

	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, HealthCareInstitution healthCareInstitution) {
		healthCareInstitutionService.updateForm(healthCareInstitution);
		return new ModelAndView("redirect:/healthcares/list");
	}

	@GetMapping("/remove/{id}")
	public ModelAndView inativar(@PathVariable("id") Long id) {
		HealthCareInstitution healthCareInstitution = healthCareInstitutionService.findById(id);

		healthCareInstitutionService.delete(healthCareInstitution);
		logger.info("Instituição de Saúde excluído");

		return new ModelAndView("redirect:/healthcares/list");
	}

}
