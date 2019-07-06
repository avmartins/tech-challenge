package pixeon.tech.challenge.init;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pixeon.tech.challenge.entity.Exam;
import pixeon.tech.challenge.entity.HealthCareInstitution;
import pixeon.tech.challenge.entity.User;
import pixeon.tech.challenge.entity.UserRole;
import pixeon.tech.challenge.repository.RegistroGeralRepository;
import pixeon.tech.challenge.service.ExamService;
import pixeon.tech.challenge.service.HealthCareInstitutionService;
import pixeon.tech.challenge.service.UserService;

@SuppressWarnings("ALL")
@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	RegistroGeralRepository registroGeralRepository;

	private final UserService userService;
	private final HealthCareInstitutionService healthCareInstitutionService;
	private final ExamService examService;

	public Init(UserService userService, HealthCareInstitutionService healthCareInstitutionService,
			ExamService examService) {
		this.userService = userService;
		this.healthCareInstitutionService = healthCareInstitutionService;
		this.examService = examService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		LocalDateTime localDateTime = LocalDateTime.now();
		int dia = 366;

		HealthCareInstitution healthCareInstitution = new HealthCareInstitution();
		healthCareInstitution.setCnpj("123.456.789-01");
		healthCareInstitution.setName("Health Care 1");
		healthCareInstitution.setMoeda(20);
		healthCareInstitution.setQtdMoedaUsada(0);

		healthCareInstitution = healthCareInstitutionService.save(healthCareInstitution);

		Exam exam = new Exam();
		exam.setHealthCareInstitution(healthCareInstitution);
		exam.setPatientAge(10);
		exam.setPatientGender("M");
		exam.setPatientName("XXXXXX");
		exam.setPhysicianCRM("CRM");
		exam.setPhysicianName("YYYYYY");
		exam.setProcedureName("KKKKKKKK");

		exam = examService.save(exam);

		User root = new User("Administrador", "000.111.222-33", "root@email.com", "root", UserRole.ADMIN.getRole(),
				localDateTime.toLocalDate().minusDays(dia), true, healthCareInstitution);
		userService.save(root);

		User user = new User("User", "111.555.333-22", "user@email.com", "user", UserRole.USER.getRole(),
				localDateTime.toLocalDate().minusDays(dia), true, healthCareInstitution);
		userService.save(user);

	}
}
