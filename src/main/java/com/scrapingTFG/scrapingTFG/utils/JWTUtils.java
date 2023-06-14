package com.scrapingTFG.scrapingTFG.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
//import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Component
public class JWTUtils {

    @Value("${security.jwt.secret}")
    private String key;
    @Value("${security.jwt.issuer}")
    private String issuer;
    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;
    private final Logger log = LoggerFactory.getLogger(JWTUtils.class);

    public String create(String id, String subject) { //CREA EL JWT

        // The JWT signature algorithm used to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //  sign JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //  set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */

    //ESTAS DOS FUNCIONES NOS DEVUELVEN INFORMACION QUE LE HAYAMOS AGREGADO AL TOKEN(nombre de usuario,id,email)
    public String getValue(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public String getKey(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getId();
    }



/*   ---- funciones que tenía antes de cambiarlo

    public void JWTUtil() {
    }
    public String create(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(this.issuer).signWith(this.toKey(this.key), SignatureAlgorithm.HS256);
        if (this.ttlMillis >= 0) {
            long expMillis = nowMillis + this.ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    private Claims obtenerClaims(String jws) {
        try {
            Claims claims = (Claims)Jwts.parserBuilder().setSigningKey(this.toKey(this.key)).build().parseClaimsJws(jws).getBody();
            return claims;
        } catch (JwtException var4) {
            return null;
        }
    }

    public String getValue(String jwt) {
        Claims claims = this.obtenerClaims(jwt);
        return claims == null ? null : claims.getSubject();
    }

    public String getKey(String jwt) {
        //Claims claims = this.obtenerClaims(jwt);
        Claims claims = this.obtenerClaims(jwt);
        return claims == null ? null : claims.getId();
    }

    private Key toKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

*/

}
