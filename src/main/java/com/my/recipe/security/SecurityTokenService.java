package com.my.recipe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class SecurityTokenService {

  // TODO: Should change with better secretkey.
  private static final String SECRET_KEY = "recipeManagement";
  private static final long EXPIRATION_TIME = 86400000L;

  // Generate a JWT token
  public String generateToken(String username, Long userId, Boolean isAdmin) {
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    return JWT.create()
        .withClaim("sub", username)
        .withClaim("userId", userId)
        .withClaim("roles", isAdmin)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(algorithm);
  }

  public String extractUsername(String token) {
    return extractClaim(token, "sub");
  }

  public Long extractUserId(String token) {
    return Long.parseLong(extractClaim(token, "userId"));
  }

  public Boolean extractRoles(String token) {
    return JWT.decode(token).getClaim("roles").asBoolean();
  }

  private String extractClaim(String token, String claim) {
    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getClaim(claim).asString();
  }

  public boolean validateToken(String token, String username) {
    return (username.equals(extractUsername(token)) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getExpiresAt();
  }
}
