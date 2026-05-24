package com.iesagora.jesus.apptienda.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.iesagora.jesus.apptienda.business.model.Usuario;

import io.jsonwebtoken.Claims;

@Component
public class JwtUtils {
	
	@Value("${apptienda.secreto.jwt}")
	private String jwtKey;
	
	@Value("${apptienda.tiempo.expiracion.jwt}")
	private long jwtExpira;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return io.jsonwebtoken.Jwts
							.parserBuilder()
							.setSigningKey(getSigningKey())
							.build()
							.parseClaimsJws(token)
							.getBody();
	}
	
	private java.security.Key getSigningKey(){
		byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(jwtKey);
		return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());	
	}
	
	public boolean validacionToken(String token, Usuario usuario) {
		final String username = extractUsername(token);
		return (username.equals(usuario.getId().toString()) && !isTokenExpired(token));
	}
	
	public String generarToken(Usuario usuario) {
		Map<String, Object> claims = new HashMap<>();	
		
		claims.put("id", usuario.getId());
		claims.put("rol", usuario.getRol().name());
		
		return crearToken(claims, usuario.getId().toString());
	}
	
	private String crearToken(Map<String, Object> claims, String subject) {
		return io.jsonwebtoken.Jwts.builder()
							.setClaims(claims)
							.setSubject(subject)
							.setIssuedAt(new Date(System.currentTimeMillis()))
							.setExpiration(new Date(System.currentTimeMillis() + jwtExpira))
							.signWith(getSigningKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
							.compact();
	}
	
}
