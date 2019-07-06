package pixeon.tech.challenge.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Anderson Virginio Martins
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = Logger.getLogger(AuthController.class);

	@GetMapping(value = "/login")
	public ModelAndView login(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {

		if (error) {
			model.addAttribute("error", "Nome de usuário ou senha inválidos!");
			logger.info("Nome de usuário ou senha inválidos!");
			return new ModelAndView("login", model);
		}
		return new ModelAndView("redirect:/");
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		logger.info("Usuário deslogado com sucesso!");
		return new ModelAndView("redirect:/");
	}

	@GetMapping(value = "/denied")
	public ModelAndView accessDenied() {
		return new ModelAndView("405");
	}

}
