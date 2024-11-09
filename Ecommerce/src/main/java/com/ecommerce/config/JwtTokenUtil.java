package com.ecommerce.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ecommerce.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtTokenUtil implements Serializable {
private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 60;

	private String secret="RkqzhzEjo6xuKFPb4jztckDGFz6vLw3+jXuHeWZ9iFINRpflDeJ1VadCMrCuDS0WJWwHRwveR15UzaEiFyDFP6zZ4synqT+DrPaWXJpQ9qum1HB1ORBN/dvLALGE2zDc2uHSEoLxeXEGVQYp";

	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) 
	{
		
		
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(User user) 
	{
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, user.getUserId()+"");
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) 
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	    String token = Jwts.builder()
	            .signWith(secretKey, SignatureAlgorithm.HS256)
	            .setHeaderParam("typ", "jwt")
	            .setIssuer("bloodbank")
	            .setAudience("user")
	            .setSubject(subject)
	            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
	            .compact();
	    return token;

	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, User user) {
		final String userid = getUserIdFromToken(token);
		String id = user.getUserId()+"";
		return (id.equals(userid)) && !isTokenExpired(token);
	}
}
