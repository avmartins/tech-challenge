package pixeon.tech.challenge.entity;

import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @author Anderson Virginio Martins
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	private User user;

	public CurrentUser(User user) {
		super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserRole().toString()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

	public String getRole() {
		return user.getUserRole();
	}
}
