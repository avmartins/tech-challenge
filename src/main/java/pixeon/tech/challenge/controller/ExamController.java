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

import pixeon.tech.challenge.entity.CurrentUser;
import pixeon.tech.challenge.entity.Exam;
import pixeon.tech.challenge.exception.ExamNotFoundException;
import pixeon.tech.challenge.exception.HealthCarePermissionException;
import pixeon.tech.challenge.service.ExamService;
import pixeon.tech.challenge.service.HealthCareInstitutionService;

@RestController
@RequestMapping("/exams")
public class ExamController {

	private static final Logger logger = Logger.getLogger(ExamController.class);

	private final ExamService examService;
	private final HealthCareInstitutionService healthCareInstitutionService;

	public ExamController(ExamService examService, HealthCareInstitutionService healthCareInstitutionService) {
		this.examService = examService;
		this.healthCareInstitutionService = healthCareInstitutionService;
	}

	@GetMapping("list")
	public ModelAndView list(ModelMap modelMap) {
		modelMap.addAttribute("exams", examService.findAll());
		return new ModelAndView("/fragments/exams/list", modelMap);
	}

	@GetMapping("/new")
	public ModelAndView newExam(ModelMap modelMap) {
		modelMap.addAttribute("exam", new Exam());
		modelMap.addAttribute("healthCares", healthCareInstitutionService.findAll());
		return new ModelAndView("/fragments/exams/new", modelMap);
	}

	@PostMapping("/save")
	public ModelAndView saveExam(@ModelAttribute("exam") Exam exam) {
		try {

			// Uma instituição de saúde não tem permissão para criar ou obter um exame
			// quando estiver sem orçamento.
			if (exam.getHealthCareInstitution().getQtdMoedaSaldo() == 0)
				throw new HealthCarePermissionException("id-" + exam.getHealthCareInstitution().getId());

			// Cada exame criado com sucesso deve cobrar 1 pixe de moeda do bugdet da
			// instituição de saúde
			int qtd = 0;
			if (exam.getId() == null) {
				qtd = exam.getHealthCareInstitution().getQtdMoedaUsada() + 1;
			}

			// grava exam
			examService.save(exam);

			// atualiza HealthCareInstitution Qtd Moeda Usada e Exam recovered
			if (qtd != 0) {
				exam.getHealthCareInstitution().setQtdMoedaUsada(qtd);
				exam.setExamRecovered(false);
				exam = examService.save(exam);
			}
		} catch (Exception e) {
			logger.info("Erro ao incluir o exame");
		}

		return new ModelAndView("redirect:/exams/list");
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {

		CurrentUser currentUser = (CurrentUser) model.get("currentUser");

		Exam exam = examService.findById(id);

		if (exam == null)
			throw new ExamNotFoundException("id-" + id);

		// Uma instituição de saúde não deve ter acesso a um exame que pertence a outra
		// instituição de saúde
		if (exam.getHealthCareInstitution().getId() != currentUser.getUser().getHealthCareInstitution().getId())
			throw new HealthCarePermissionException("id-" + currentUser.getUser().getHealthCareInstitution().getId());

		// Você deve cobrar 1 pixe de moeda do orçamento quando uma instituição de saúde
		// recupera um exame,
		// mas se a instituição recupera o mesmo exame mais de uma vez, você não deve
		// cobrar,
		// o que significa que você deve cobrar apenas o primeiro acesso ao exame.
		if (!exam.isExamRecovered()) {

			// Uma instituição de saúde não tem permissão para criar ou obter um exame
			// quando estiver sem orçamento.
			if (exam.getHealthCareInstitution().getQtdMoedaSaldo() == 0)
				throw new HealthCarePermissionException("id-" + exam.getHealthCareInstitution().getId());

			int qtd = exam.getHealthCareInstitution().getQtdMoedaUsada() + 1;
			exam.getHealthCareInstitution().setQtdMoedaUsada(qtd);
			exam.setExamRecovered(true);
			exam = examService.save(exam);
		}

		// Você deve cobrar 1 pixe de moeda do orçamento quando uma instituição de saúde
		// recupera um exame,
		// mas se a instituição recupera o mesmo exame mais de uma vez, você não deve
		// cobrar,
		// o que significa que você deve cobrar apenas o primeiro acesso ao exame.
		if (!exam.isExamRecovered()) {

			// Uma instituição de saúde não tem permissão para criar ou obter um exame
			// quando estiver sem orçamento.
			if (exam.getHealthCareInstitution().getQtdMoedaSaldo() == 0)
				throw new HealthCarePermissionException("id-" + exam.getHealthCareInstitution().getId());

			int qtd = exam.getHealthCareInstitution().getQtdMoedaUsada() + 1;
			exam.getHealthCareInstitution().setQtdMoedaUsada(qtd);
			exam.setExamRecovered(true);
			exam = examService.save(exam);
		}

		model.addAttribute("exam", exam);
		model.addAttribute("healthCares", healthCareInstitutionService.findAll());
		return new ModelAndView("fragments/exams/edit", model);
	}

	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, Exam exam) {
		examService.updateForm(exam);
		return new ModelAndView("redirect:/exams/list");
	}

	@GetMapping("/remove/{id}")
	public ModelAndView remove(@PathVariable("id") Long id) {
		Exam exam = examService.findById(id);

		examService.delete(exam);
		logger.info("Exame excluído");

		return new ModelAndView("redirect:/exams/list");
	}

}
