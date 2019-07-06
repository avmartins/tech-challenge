package pixeon.tech.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pixeon.tech.challenge.entity.HealthCareInstitution;
import pixeon.tech.challenge.repository.HealthCareInstitutionRepository;
import pixeon.tech.challenge.service.HealthCareInstitutionService;

@Service
public class HealthCareInstitutionServiceImpl implements HealthCareInstitutionService {

	private final HealthCareInstitutionRepository healthCareInstitutionRepository;

	@Autowired
	public HealthCareInstitutionServiceImpl(HealthCareInstitutionRepository healthCareInstitutionRepository) {
		this.healthCareInstitutionRepository = healthCareInstitutionRepository;
	}

	@Override
	public Iterable<HealthCareInstitution> findAll() {
		return healthCareInstitutionRepository.findAll();
	}

	@Override
	public HealthCareInstitution save(HealthCareInstitution healthCareInstitution) {
		healthCareInstitution.setMoeda(20);
		healthCareInstitution.setQtdMoedaUsada(0);
		return healthCareInstitutionRepository.save(healthCareInstitution);
	}

	@Override
	public void updateForm(HealthCareInstitution healthCareInstitution) {
		HealthCareInstitution h = healthCareInstitutionRepository.findById(healthCareInstitution.getId()).orElse(null);

		h.setCnpj(healthCareInstitution.getCnpj());
		h.setName(healthCareInstitution.getName());
		healthCareInstitutionRepository.save(h);
	}

	@Override
	public HealthCareInstitution findById(Long id) {
		return healthCareInstitutionRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(HealthCareInstitution healthCareInstitution) {
		HealthCareInstitution h = healthCareInstitutionRepository.findById(healthCareInstitution.getId()).orElse(null);

		healthCareInstitutionRepository.delete(h);
	}

}
