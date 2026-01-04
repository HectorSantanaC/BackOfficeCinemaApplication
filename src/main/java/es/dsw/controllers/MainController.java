package es.dsw.controllers;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.dsw.connections.MySqlConnection;
import es.dsw.daos.GuardarPeliculasDAO;
import es.dsw.daos.ListarPeliculasDAO;
import es.dsw.daos.UserDAO;
import es.dsw.models.PeliculaModel;
import es.dsw.models.RespuestaListarPeliculas;
import es.dsw.models.RespuestaGuardarPelicula;
import es.dsw.models.UserModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {

	@GetMapping(value = { "/", "/home" })
	public String home(Model model, Authentication authentication, HttpServletResponse response) {

		// Username del usuario logueado
		String username = authentication.getName();

		// Consultar el usuario desde la BD
		UserDAO userDAO = new UserDAO();
		UserModel user = (UserModel) userDAO.getUserByUsername(username);

		model.addAttribute("user", user);

		// Fecha actual
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String fechaActual = LocalDateTime.now().format(formatter);

		// Codificar fechaActual para añadirla a la cookie
		String fechaParaCookie = URLEncoder.encode(fechaActual, StandardCharsets.UTF_8);

		// Crear o modificar la cookie
		Cookie cookie = new Cookie("cookie", String.valueOf(fechaParaCookie));
		
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

	@GetMapping(value = "/listarPeliculas")
	@ResponseBody
	public RespuestaListarPeliculas listarPeliculas() {

		RespuestaListarPeliculas respuesta = new RespuestaListarPeliculas(null, false, null);
		ListarPeliculasDAO peliculasDAO = new ListarPeliculasDAO();

		respuesta.setPeliculas(peliculasDAO.listarPeliculas());
		respuesta.setError(false);
		respuesta.setMsgError(null);

		return respuesta;
	}

	@PostMapping(value = "/guardarPelicula", produces = "application/json")
	@ResponseBody
	public RespuestaGuardarPelicula guardarPelicula(Authentication authentication,
			@RequestParam String titulo,
			@RequestParam(required = false) String synopsis,
			@RequestParam String genero,
			@RequestParam(required = false) String director,
			@RequestParam(required = false) String reparto,
			@RequestParam String anio,
			@RequestParam(required = false) String fechaEstreno,
			@RequestParam(required = false) String distribuidor,
			@RequestParam String pais) {

		RespuestaGuardarPelicula respuesta = new RespuestaGuardarPelicula(false, null);
		MySqlConnection mySqlConnection = new MySqlConnection(false);
		mySqlConnection.open();

		try {
			if (mySqlConnection.isError()) {
				throw new RuntimeException(mySqlConnection.msgError());
			}

			// Validación
			if (titulo.isBlank() || genero.isBlank() || anio.isBlank() || pais.isBlank()) {
				throw new RuntimeException("Título, Género, Año y País son obligatorios");
			}

			// Obtener id del usuario autenticado
			String username = authentication.getName();
			UserDAO userDAO = new UserDAO();
			UserModel userModel = (UserModel) userDAO.getUserByUsername(username);
			int idUser = (userModel != null) ? userModel.getIdUser() : -1;

			PeliculaModel pelicula = new PeliculaModel(titulo.trim(), synopsis.trim(), 
					genero, director.trim(), reparto.trim(),anio, fechaEstreno, 
					distribuidor, pais);

			GuardarPeliculasDAO guardarPeliculasDAO = new GuardarPeliculasDAO(mySqlConnection);
			int idPelicula = guardarPeliculasDAO.guardarPelicula(pelicula, idUser);

			if (idPelicula <= 0) {
				throw new RuntimeException("Error al insertar la película en base de datos");
			}

			mySqlConnection.commit();
			respuesta.setMsgError("Película '" + titulo + "' guardada correctamente.");

		} catch (Exception e) {
			mySqlConnection.rollback();
			respuesta.setError(true);
			respuesta.setMsgError(e.getMessage());
			System.out.println("Rollback: " + e.getMessage());
			e.printStackTrace();
		} finally {
			mySqlConnection.close();
		}

		return respuesta;
	}

	@GetMapping("/listarGeneros")
	@ResponseBody
	public List<String> listarGeneros() {
		return new ListarPeliculasDAO().listarGeneros();
	}

	@GetMapping("/listarDistribuidores")
	@ResponseBody
	public List<String> listarDistribuidores() {
		return new ListarPeliculasDAO().listarDistribuidores();
	}

	@GetMapping("/listarPaises")
	@ResponseBody
	public List<String> listarPaises() {
		return new ListarPeliculasDAO().listarPaises();
	}
}
