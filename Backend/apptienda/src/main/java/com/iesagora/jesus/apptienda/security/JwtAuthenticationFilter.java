package com.iesagora.jesus.apptienda.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {    
    	final String authHeader = request.getHeader("Authorization");
    	final String jwt;
    	final Long id;
    	
        System.out.println("JWT FILTER EJECUTADO");
        System.out.println("Auth header: " + authHeader);
    	
    	String path = request.getServletPath();

        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
    	
    	if(authHeader == null || !authHeader.startsWith("Bearer ")) {
        	filterChain.doFilter(request, response);
        	return;
    	}
    	jwt = authHeader.substring(7);
    	id = jwtUtils.extractClaim(jwt, claims -> claims.get("id", Long.class));
    	
    	if(id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    		var usuario = usuarioRepository.findById(id).orElse(null);    		
    		if(usuario != null && jwtUtils.validacionToken(jwt, usuario)) {
    			
    		    System.out.println("Usuario: " + usuario.getEmail());
    		    System.out.println("Authorities: " + usuario.getAuthorities());
    			
    			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    			
    			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    			SecurityContextHolder.getContext().setAuthentication(authToken);
    			
    			 System.out.println("AUTH SET: " + authToken);

    			    SecurityContextHolder.getContext().setAuthentication(authToken);

    			    System.out.println("CONTEXT AUTH: " + SecurityContextHolder.getContext().getAuthentication());
    			
    		}
    		
    	}
        
    	filterChain.doFilter(request, response);
    }
}
