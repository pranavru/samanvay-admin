package com.samanvay.admin.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.samanvay.admin.services.CustomUserDetailsService;
import com.samanvay.admin.services.JWTTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  
  @Autowired
  private JWTTokenService jwtTokenService;

  @Autowired
  ApplicationContext context;

  @Override
  @SuppressWarnings("null")
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");

    String token = null;
    String username = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
        username = jwtTokenService.extractUsername(token);
    }
    
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      CustomUserDetailsService userDetailsService = context.getBean(CustomUserDetailsService.class);

      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      if (jwtTokenService.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
