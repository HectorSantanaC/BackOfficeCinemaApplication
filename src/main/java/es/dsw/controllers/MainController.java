package es.dsw.controllers;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.daos.UserDAO;
import es.dsw.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {

	@GetMapping(value = {"/", "/home"})
	public String home(Model model, Authentication authentication, HttpServletResponse response) {
		
		// Username del usuario logueado
		String username = authentication.getName();
		
		// Consultar el usuario desde la BD
		UserDAO userDAO  = new UserDAO ();
		User user = (User) userDAO.getUserByUsername(username);
		
		model.addAttribute("user", user);
		
		// Fecha actual
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String fechaActual = LocalDateTime.now().format(formatter);
		
		// Codificar fechaActual para añadirla a la cookie
		String fechaParaCookie = URLEncoder.encode(fechaActual, StandardCharsets.UTF_8);
		
		// Crear o modificar la cookie
		Cookie cookie = new Cookie("cookie", String.valueOf(fechaParaCookie));;
		cookie.setMaxAge(7 * 24 * 60 * 60);
		
		response.addCookie(cookie);
		
		return "home";
	}
	
	@GetMapping(value = "/login")
	public String login(@CookieValue(name = "cookie", required = false) String lecturaCookie, Model model) {
		
		if (lecturaCookie != null) {
	        // Decodificar la cookie
	        String fechaDecodificada = URLDecoder.decode(lecturaCookie, StandardCharsets.UTF_8);
	        model.addAttribute("ultimoAcceso", "Fecha del último acceso: " + fechaDecodificada);
	    }
		
		return "login";
	}
}
