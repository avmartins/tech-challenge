package pixeon.tech.challenge.service.impl;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pixeon.tech.challenge.entity.CurrentUser;
import pixeon.tech.challenge.entity.User;
import pixeon.tech.challenge.service.UserService;

/**
 * @author Anderson Virginio Martins
 */
@Component
public class CurrentUserDetailsService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {

	private static final Logger logger = Logger.getLogger(CurrentUserDetailsService.class);
	private final UserService userService;

	@Autowired
	public CurrentUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user;
		try {
			user = userService.findByEmail(email);
		} catch (Exception ex) {
			logger.info("Não foi encontrado o usuário {" + email + "}");
			throw new UsernameNotFoundException("Não foi encontrado o usuário {" + email + "}");
		}
		return new CurrentUser(user);
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String email = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
		User user = userService.findByEmail(email);
		LocalDateTime localDateTime = LocalDateTime.now();
		user.setDataUltimoLogin(localDateTime);
		userService.update(user);

	}
}
