package z.group.Zchat.Configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class JwtConfig {
    public SecretKey key ;
    public  JwtConfig() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
        key = generator.generateKey();
    }
    public String getJwt(String username){
        return Jwts.builder()
                .subject(username)
                .signWith(key)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60 ) )
                .compact();
    }

    public String getusername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean checkexpiry(String token){
        return !extractClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }
    public Claims extractClaims(String token){
         return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
