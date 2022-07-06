package com.rollin.boardService.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class SecurityService {
    @Value("${jwt.secret_key}")
    String SECRET_KEY;
    @Value("${jwt.expTime}")
    long expTime;
    public String createToken(String subject){
        log.info(SECRET_KEY);
        log.info(""+expTime);
        if(expTime<=0){
            throw new RuntimeException();
        }
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signatureKey =
                new SecretKeySpec(secretKeyBytes
                        ,signatureAlgorithm.getJcaName()
                );

        return Jwts.builder()
                .setSubject(subject)
                .signWith(signatureKey,signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis()+expTime))
                .compact();
    }
    public String getSubject(String tokenBearer){
        String token=tokenBearer.substring("Bearer ".length()) ; //Authorization에 담아 보낼땐 Bearer를 토큰 앞에 붙여서 보내야하기 때문에(약속이다) token에 Bearer가 앞에 붙여져 있으니 잘라준다
        log.info(token);
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public Integer getIdAtToken(){

        //header에서 빼오는 작업
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //토근에서 id값 빼오는 작업
        String id=request.getHeader("Authorization");//token을 parameter로 보내는게 아니라 header에 Authorization로 담아 보낸다
        log.info(id);

        return Integer.parseInt(getSubject(id));

    }
}
