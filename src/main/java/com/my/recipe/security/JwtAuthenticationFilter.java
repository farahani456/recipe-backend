package com.my.recipe.security;

import com.my.recipe.services.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final SecurityTokenService securityTokenService;
  private final IAuthenticationService authenticationService;

  public JwtAuthenticationFilter(
      SecurityTokenService securityTokenService, IAuthenticationService authenticationService) {
    this.securityTokenService = securityTokenService;
    this.authenticationService = authenticationService;
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);
    }
    return null;
  }

  @Override
  protected void doFilterInternal(
      jakarta.servlet.http.HttpServletRequest request,
      HttpServletResponse response,
      jakarta.servlet.FilterChain filterChain)
      throws jakarta.servlet.ServletException, IOException {
    String path = request.getServletPath();
    if ("/api/v1/auth/login".equals(path)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = getTokenFromRequest(request);
    if (token != null
        && securityTokenService.validateToken(token, securityTokenService.extractUsername(token))) {
      String username = securityTokenService.extractUsername(token);
      AuthDetailsDTO userDetails = authenticationService.getAuthDetails(username);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}
