package edu.uni.lodz.system.akademia.pilkarska.security.jwt;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
@Slf4j
@PropertySource(value = {"classpath:jwtToken.properties"})
public class JwtUtils {
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenFromUsername(String username){
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }



    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty {}", e.getMessage());
        }
        return false;
    }
}