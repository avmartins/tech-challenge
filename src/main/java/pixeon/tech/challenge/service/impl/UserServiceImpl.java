package pixeon.tech.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pixeon.tech.challenge.entity.User;
import pixeon.tech.challenge.repository.UserRepository;
import pixeon.tech.challenge.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void save(User user) {
		String passwordHash = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(passwordHash);
		userRepository.save(user);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateForm(User user) {
		User u = userRepository.findById(user.getId()).orElse(null);
		u.setNome(user.getNome());
		u.setCpf(user.getCpf());
		u.setEmail(user.getEmail());
		if (user.getPassword() != null) {
			String passwordHash = new BCryptPasswordEncoder().encode(user.getPassword());
			u.setPassword(passwordHash);
		}
		userRepository.save(u);
	}

	@Override
	public Integer countAllInvestors() {
		return userRepository.countAllInvestors();
	}

	@Override
	public User findByCpf(String cpf) {
		return userRepository.findByCpf(cpf);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public Iterable<User> findActiveUser() {
		return userRepository.findByActive();
	}

}
