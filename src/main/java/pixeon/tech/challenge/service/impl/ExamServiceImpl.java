package pixeon.tech.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pixeon.tech.challenge.entity.Exam;
import pixeon.tech.challenge.repository.ExamRepository;
import pixeon.tech.challenge.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

	private final ExamRepository examRepository;

	@Autowired
	public ExamServiceImpl(ExamRepository examRepository) {
		this.examRepository = examRepository;
	}

	@Override
	public Iterable<Exam> findAll() {
		return examRepository.findAll();
	}

	@Override
	public Exam save(Exam exam) {
		return examRepository.save(exam);
	}

	@Override
	public void update(Exam exam) {
		examRepository.save(exam);
	}

	@Override
	public void updateForm(Exam exam) {
		Exam e = examRepository.findById(exam.getId()).orElse(null);

		e.setHealthCareInstitution(exam.getHealthCareInstitution());
		e.setPatientAge(exam.getPatientAge());
		e.setPatientGender(exam.getPatientGender());
		e.setPatientName(exam.getPatientName());
		e.setPhysicianCRM(exam.getPhysicianCRM());
		e.setPhysicianName(exam.getPhysicianName());
		e.setProcedureName(exam.getProcedureName());

		examRepository.save(e);
	}

	@Override
	public void delete(Exam exam) {
		Exam e = examRepository.findById(exam.getId()).orElse(null);

		examRepository.delete(e);
	}

	@Override
	public Exam findById(Long id) {
		return examRepository.findById(id).orElse(null);
	}

}
