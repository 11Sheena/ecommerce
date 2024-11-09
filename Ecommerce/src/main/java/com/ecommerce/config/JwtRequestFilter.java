package com.ecommerce.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.entities.User;
import com.ecommerce.services.UserService;
import com.ecommerce.utils.ApiResponse;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        System.out.println(path);
        return path.contains("/web/") || path.contains("/api-docs") || path.contains("/swagger-ui/");
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain)
			throws ServletException, IOException 
	{		
		Gson gson = new Gson();
		response.setHeader("Content-Type", "application/json");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String userid = null;
		String jwtToken = null;
		System.out.println("Token Filter Run .... " + requestTokenHeader);
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) 
		{
			jwtToken = requestTokenHeader.substring(7);
			System.out.println("Token : " + jwtToken);
			try {
				userid = jwtTokenUtil.getUserIdFromToken(jwtToken);
				User user = this.userService.getById(Integer.parseInt(userid));
				System.out.println(user);
				if (jwtTokenUtil.validateToken(jwtToken, user))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							user, null, user.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					chain.doFilter(request, response);
				}else {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.getWriter().write(gson.toJson(new ApiResponse(false, "Token Not Match !")));
				}
			} catch (IllegalArgumentException e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write(gson.toJson(new ApiResponse(false, "Token Not Found !")));
			} catch (ExpiredJwtException e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write(gson.toJson(new ApiResponse(false, "Token Expired !")));
			}catch(Exception ex) {
				ex.printStackTrace();
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write(gson.toJson(new ApiResponse(false, "Wrong Token Format !")));
			}
		} else {
			System.out.println("Token Nahi Aai .... ");
			response.setStatus(HttpStatus.OK.value());
			response.getWriter().write(gson.toJson(new ApiResponse(false, "Token Not Found !")));
		}
	}

}

