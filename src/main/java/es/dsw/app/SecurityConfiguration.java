package es.dsw.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import es.dsw.daos.RolDAO;
import es.dsw.daos.UserDAO;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private static InMemoryUserDetailsManager inMemory;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authorize -> authorize
										.requestMatchers("/bootstrap/**", 
														 "/img/**", 
														 "/js/**", 
														 "/styles/**").permitAll()
										.requestMatchers("/admin").hasRole("admin")
										.requestMatchers("/userweb").hasRole("userweb")
										.requestMatchers("/commercial").hasRole("commercial")
										.requestMatchers("/basicuser").hasRole("basicuser")
														 .anyRequest().authenticated())
			.httpBasic(withDefaults())
			.formLogin(form -> form
							.loginPage("/login")
							.loginProcessingUrl("/loginprocess")
							.permitAll())
			.logout((logout) -> logout.logoutSuccessUrl("/login").permitAll());
		
		return http.build();
	}
	
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		
		UserDAO userDAO = new UserDAO();
		RolDAO rolDAO = new RolDAO();
		
		inMemory = new InMemoryUserDetailsManager();
		
		for (es.dsw.models.UserModel user : userDAO.getAll()) {
			
			List<String> roles = rolDAO.getRol(user.getIdUser());
			
			UserDetails userDetails = org.springframework.security.core.userdetails.User
					.withUsername(user.getUserName())
					.password("{noop}" + user.getPassword())
					.roles(roles.toArray(new String[0]))
					.build();
			
			inMemory.createUser(userDetails);
			
		}
		
		return inMemory;
	}
}
