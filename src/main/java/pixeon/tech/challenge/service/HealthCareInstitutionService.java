package  pixeon.tech.challenge.service;

import pixeon.tech.challenge.entity.HealthCareInstitution;

public interface HealthCareInstitutionService {

	Iterable<HealthCareInstitution> findAll();

	HealthCareInstitution findById(Long id);

	HealthCareInstitution save(HealthCareInstitution healthCareInstitution);
	
	void updateForm(HealthCareInstitution healthCareInstitution);
	
	void delete(HealthCareInstitution healthCareInstitution);

}


