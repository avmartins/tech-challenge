package pixeon.tech.challenge.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pixeon.tech.challenge.entity.Exam;
import pixeon.tech.challenge.entity.HealthCareInstitution;
import pixeon.tech.challenge.exception.ExamNotFoundException;
import pixeon.tech.challenge.exception.HealthCareNotFoundException;
import pixeon.tech.challenge.exception.HealthCarePermissionException;
import pixeon.tech.challenge.service.ExamService;
import pixeon.tech.challenge.service.HealthCareInstitutionService;

@RestController
@RequestMapping("/HealthCareExam")
@CrossOrigin(origins = { "http://localhost:8090" })
public class HealthCareExamRestController {

	private final HealthCareInstitutionService healthCareInstitutionService;
	private final ExamService examService;

	public HealthCareExamRestController(HealthCareInstitutionService healthCareInstitutionService,
			ExamService examService) {
		this.healthCareInstitutionService = healthCareInstitutionService;
		this.examService = examService;
	}

	// Create a Healthcare
	@PostMapping("/CreateHealthCare")
	public ResponseEntity<Object> createHealthCareInstitution(
			@RequestBody HealthCareInstitution healthCareInstitution) {
		HealthCareInstitution savedHealthCareInstitution = healthCareInstitutionService.save(healthCareInstitution);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedHealthCareInstitution.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	// Create an exam
	@PostMapping("/CreateExam")
	public ResponseEntity<Object> createExam(@RequestBody Exam exam) {

		// Uma instituição de saúde não tem permissão para criar ou obter um exame
		// quando estiver sem orçamento.
		if (exam.getHealthCareInstitution().getQtdMoedaSaldo() == 0)
			throw new HealthCarePermissionException("id-" + exam.getHealthCareInstitution().getId());

		// Cada exame criado com sucesso deve cobrar 1 pixe de moeda do bugdet da
		// instituição de saúde
		exam.getHealthCareInstitution().setQtdMoedaUsada(1);

		Exam savedExam = examService.save(exam);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedExam.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	// Update an exam
	@PutMapping("/UpdateExam/{id}")
	public ResponseEntity<Object> updateExam(@RequestBody Exam exam, @PathVariable long id) {

		Exam examOptional = examService.findById(id);

		if (examOptional == null)
			return ResponseEntity.notFound().build();

		exam.setId(id);

		examService.save(exam);

		return ResponseEntity.noContent().build();
	}

	// Delete an exam
	@DeleteMapping("/DeleteExam/{id}")
	public void deleteExam(@PathVariable long id) {
		Exam exam = examService.findById(id);
		examService.delete(exam);
	}

	// Get all exam
	@GetMapping("/exams")
	public Iterable<Exam> retrieveAllExams() {
		return examService.findAll();
	}

	// Get an exam by its identifier
	@GetMapping("/exams/{id}/{idHealthCareInstitution}")
	public Exam retrieveExam(@PathVariable long id, @PathVariable long idHealthCareInstitution)
			throws ExamNotFoundException, HealthCareNotFoundException, HealthCarePermissionException {
		Exam exam = examService.findById(id);
		HealthCareInstitution healthCareInstitution = healthCareInstitutionService.findById(idHealthCareInstitution);

		if (exam == null)
			throw new ExamNotFoundException("id-" + id);

		if (healthCareInstitution == null)
			throw new HealthCareNotFoundException("id-" + idHealthCareInstitution);

		// Uma instituição de saúde não deve ter acesso a um exame que pertence a outra
		// instituição de saúde
		if (healthCareInstitution.getId() != idHealthCareInstitution)
			throw new HealthCarePermissionException("id-" + idHealthCareInstitution);

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

		return exam;
	}

}
