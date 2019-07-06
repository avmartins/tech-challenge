package pixeon.tech.challenge.controller;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pixeon.tech.challenge.entity.User;
import pixeon.tech.challenge.entity.UserRole;
import pixeon.tech.challenge.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/list")
	public ModelAndView list(ModelMap modelMap) {
		// CurrentUser currentUser = (CurrentUser) modelMap.get("currentUser");

		Iterable<User> usuarios = null;

		usuarios = userService.findActiveUser();
		modelMap.addAttribute("usuarios", usuarios);
		return new ModelAndView("/fragments/users/list", modelMap);
	}

	@GetMapping("/new")
	public ModelAndView newUser(ModelMap modelMap) {
		modelMap.addAttribute("usuario", new User());
		return new ModelAndView("/fragments/users/new", modelMap);
	}

	@PostMapping("/save")
	public ModelAndView saveUser(@ModelAttribute("usuario") User user) {

		/* Testa se o usuário já é cadastrado e/ou inativo */
		User usuario = userService.findByCpf(user.getCpf());

		if (usuario == null) {
			user.setUserRole(UserRole.USER.getRole());
			user.setIsActive(true);
			userService.save(user);

			return new ModelAndView("redirect:/users/list");
		}

		if (usuario.getIsActive()) {
			logger.info("Usuário já cadastrado");
			return new ModelAndView("fragments/users/list");
		}

		if (!usuario.getIsActive()) {
			logger.info("Usuário inativo!");
			return new ModelAndView("fragments/users/list");
		}
		return new ModelAndView("redirect:/users/list");
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return new ModelAndView("fragments/users/edit", model);
	}

	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, User user) {
		userService.updateForm(user);
		return new ModelAndView("redirect:/users/list");
	}

	@GetMapping("/remove/{id}")
	public ModelAndView inativar(@PathVariable("id") Long id) {
		User user = userService.findById(id);

		user.setIsActive(false);
		userService.update(user);
		logger.info("Usuário excluído");

		return new ModelAndView("redirect:/users/list");
	}

}
