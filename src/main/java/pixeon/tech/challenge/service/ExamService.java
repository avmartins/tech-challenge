package  pixeon.tech.challenge.service;

import  pixeon.tech.challenge.entity.Exam;

public interface ExamService {

	Iterable<Exam> findAll();

	Exam findById(Long id);

	Exam save(Exam Exam);

	void update(Exam Exam);

	void updateForm(Exam Exam);
	
	void delete(Exam Exam);

}
