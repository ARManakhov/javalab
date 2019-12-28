package dev.sirosh;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.sirosh.models.User;
import dev.sirosh.models.UserBuilder;

import java.util.Map;

public class TokenizeUser {
    private final String SECRET_KEY = "secret";

    public String createJWT(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String token = JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("username", user.getUsername())
                    .withClaim("role", user.getRole())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public User decodeJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            User user = UserBuilder.anUser()
                    .withId(claims.get("id").asLong())
                    .withUsername(claims.get("username").asString())
                    .withRole(claims.get("role").asString())
                    .build();
            return user;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}
