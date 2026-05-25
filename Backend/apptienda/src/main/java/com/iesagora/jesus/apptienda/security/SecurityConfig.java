package com.iesagora.jesus.apptienda.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
	
	@Bean
	public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
	    return email -> {
	    	Usuario usuario = usuarioRepository.findByEmail(email);
	    	if(usuario == null)
	    		throw new UsernameNotFoundException("Usuario no encontrado: " + email);
	    	return usuario;
	    };
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
		
		// Configuracion Cors
		
		http.cors(cors -> cors.configurationSource(request -> {
	        var corsConfig = new org.springframework.web.cors.CorsConfiguration();
	        corsConfig.setAllowCredentials(true);
	        //Se dejara el localhost para hacer comprovaciones en local y no tener que subirlo a GitHub sin probarlo
	        corsConfig.addAllowedOrigin("http://localhost:4200");
	        corsConfig.addAllowedOrigin("https://apptiendaropadam-production-41ce.up.railway.app");
	        corsConfig.addAllowedHeader("*");
	        corsConfig.addAllowedMethod("*");
	        return corsConfig;
	    }));
		
		// Configuracion de Seguridad aqui van todas las URL
		
		http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/empresa/**").authenticated()
                .requestMatchers("/home/**").permitAll()
                .requestMatchers("/user/**").authenticated()
                .anyRequest().authenticated())
        		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        		.authenticationProvider(authenticationProvider)
        		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
	                                                     PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder);
	    return authProvider;
	}
	
	

	
}
