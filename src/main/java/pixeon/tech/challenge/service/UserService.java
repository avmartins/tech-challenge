package  pixeon.tech.challenge.service;

import  pixeon.tech.challenge.entity.User;

public interface UserService {

	Iterable<User> findAll();

	User findById(Long id);

	User findByEmail(String email);

	User findByCpf(String cpf);

	Iterable<User> findActiveUser();

	void save(User user);

	void update(User user);

	void updateForm(User user);

	Integer countAllInvestors();

}
