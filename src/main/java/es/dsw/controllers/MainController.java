package es.dsw.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.daos.UserDAO;
import es.dsw.models.User;

@Controller
public class MainController {

	@GetMapping(value = {"/", "/home"})
	public String home(Model model, Authentication authentication) {
		
		// Username del usuario logueado
		String username = authentication.getName();
		
		// Consultar el usuario desde la BD
		UserDAO userDAO  = new UserDAO ();
		User user = (User) userDAO.getUserByUsername(username);
		
		model.addAttribute("user", user);
		
		return "home";
	}
	
	@GetMapping(value = "/login")
	public String login() {		
		return "login";
	}
}
