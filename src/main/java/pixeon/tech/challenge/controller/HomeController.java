package pixeon.tech.challenge.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pixeon.tech.challenge.entity.RegistroGeral;
import pixeon.tech.challenge.entity.User;
import pixeon.tech.challenge.entity.UserRole;
import pixeon.tech.challenge.service.RegistroGeralService;
import pixeon.tech.challenge.service.UserService;

@RestController
@RequestMapping("/")
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);

	private final UserService userService;
	private final RegistroGeralService registroGeralService;

	public HomeController(UserService userService, RegistroGeralService registroGeralService) {
		this.userService = userService;
		this.registroGeralService = registroGeralService;
	}

	@GetMapping("")
	public ModelAndView auth() {
		return new ModelAndView("login");
	}

	@GetMapping("dashboard")
	public ModelAndView novo(Principal principal, ModelMap modelMap) throws IOException {

		/* Dados do login */
		User user = userService.findByEmail(principal.getName());

		if (!user.getIsActive()) {
			modelMap.addAttribute("Usuário inativo");
			logger.info("Usuário inativo!");
			return new ModelAndView("redirect:/auth/logout", modelMap);
		}

		String userRole = user.getUserRole();
		userService.update(user);

		/* Sessão do Administrador */
		if (userRole.equals(UserRole.ADMIN.getRole())) {

			Iterable<RegistroGeral> usuarios = registroGeralService.findAllByDataAndUserRoleIsAdministrador();

			ArrayList<BigDecimal> point = new ArrayList<>();
			ArrayList<String> label = new ArrayList<>();
			ArrayList<Double> rend = new ArrayList<>();

			/* Enviando objetos para a View */
			modelMap.addAttribute("label", label);
			modelMap.addAttribute("point", point);
			modelMap.addAttribute("point", point);
			modelMap.addAttribute("rend", rend);
			modelMap.addAttribute("usuario", user);
			modelMap.addAttribute("usuarios", usuarios);
			return new ModelAndView("fragments/administrador/dashboardv2", modelMap);
		}

		/* Sessão do User */
		if (userRole.equals(UserRole.USER.getRole())) {

			Iterable<RegistroGeral> usuarios = registroGeralService.findAllByDataAndUserRoleIsAdministrador();

			ArrayList<BigDecimal> point = new ArrayList<>();
			ArrayList<String> label = new ArrayList<>();
			ArrayList<Double> rend = new ArrayList<>();

			modelMap.addAttribute("label", label);
			modelMap.addAttribute("point", point);
			modelMap.addAttribute("rend", rend);
			modelMap.addAttribute("usuario", user);
			modelMap.addAttribute("usuarios", usuarios);

			return new ModelAndView("fragments/administrador/dashboardv2", modelMap);
		}
		return new ModelAndView("redirect:/");

	}

}
