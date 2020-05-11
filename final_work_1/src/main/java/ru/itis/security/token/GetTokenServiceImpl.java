package ru.itis.security.token;

import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.security.details.UserDetailsServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetTokenServiceImpl implements GetTokenService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String getToken(String username, String password) throws Exception {
        if (username == null || password == null)
            return null;
        UserDetailsImpl udi = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        User user = udi.getUser();

        Map<String, Object> tokenData = new HashMap<>();
        if (password.equals(user.getHashPassword())) {
            String SECRET_KEY = "secret";
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            tokenData.put("clientType", "user");
            tokenData.put("userID", String.valueOf(user.getId()));
            tokenData.put("username", user.getName());
            tokenData.put("token_create_date", new Date().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 100);
            tokenData.put("token_expiration_date", calendar.getTime());
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);
            String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
            return token;
        } else {
            throw new Exception("Authentication error");
        }
        
    }

}
