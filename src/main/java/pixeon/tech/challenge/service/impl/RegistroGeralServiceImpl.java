package pixeon.tech.challenge.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pixeon.tech.challenge.entity.RegistroGeral;
import pixeon.tech.challenge.entity.User;
import pixeon.tech.challenge.repository.RegistroGeralRepository;
import pixeon.tech.challenge.service.RegistroGeralService;

@Service
public class RegistroGeralServiceImpl implements RegistroGeralService {

	private final RegistroGeralRepository registroGeralRepository;

	@Autowired
	public RegistroGeralServiceImpl(RegistroGeralRepository registroGeralRepository) {
		this.registroGeralRepository = registroGeralRepository;
	}

	@Override
	public void atualizacaoNoLogIn() {
	}

	@Override
	public void primeiroRegistro(User user, LocalDate localDate) {
		RegistroGeral primeiroRegistro = new RegistroGeral();
		primeiroRegistro.setUser(user);
		primeiroRegistro.setUserRole(user.getUserRole());
		primeiroRegistro.setData(localDate);
		primeiroRegistro.setSaldoAnterior(new BigDecimal("0"));
		primeiroRegistro.setSaldoAtual(new BigDecimal("0"));
		primeiroRegistro.setCotaAtual(0);
		primeiroRegistro.setCotaAnterior(0);
		primeiroRegistro.setSaldoTxt(new BigDecimal("0"));
		primeiroRegistro.setSomaDepositos(new BigDecimal("0"));
		registroGeralRepository.save(primeiroRegistro);
	}

	@Override
	public RegistroGeral ultimoRegistroDoUsuario(User user) {
		LocalDate data = LocalDate.now();
		return registroGeralRepository.findByDataAndUser(data, user);
	}

	@Override
	public Iterable<RegistroGeral> findAllByData() {
		LocalDate data = LocalDate.now();
		return registroGeralRepository.findAllByData(data);
	}

	@Override
	public Iterable<RegistroGeral> findByUser(User user) {
		return registroGeralRepository.findByUserOrderByData(user);
	}

	@Override
	public Iterable<RegistroGeral> findAllByDataAndUserRoleIsUser() {
		return registroGeralRepository.findAllByDataAndUserRoleIsUser();
	}

	@Override
	public Iterable<RegistroGeral> findAllByDataAndUserRoleIsAdministrador() {
		return registroGeralRepository.findAllByDataAndUserRoleIsAdministrador();
	}

	@Override
	public Iterable<RegistroGeral> findAll() {
		return registroGeralRepository.findAll();
	}

}