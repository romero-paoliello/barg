package com.barganha.barganha.service;

import com.barganha.barganha.model.data.AccountData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.hasLength;

@Component
public class SessionServiceImpl implements SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    // TODO externalize
    private static final String SECRET = "galodoido";

    private static final String AUTH_HEADER_PARAM_NAME = "Authorization";

    private static ThreadLocal<Claims> threadLocal;

    @Override
    public boolean isLoggedIn() {
        return threadLocal != null && threadLocal.get().getSubject() != null;
    }

    @Override
    public String create(AccountData ad) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", ad.getUserId());
        map.put("email", ad.getEmail());

        logger.debug("Creating user session - email={}", ad.getEmail());

        return Jwts.builder()
                .setSubject(ad.getUserId().toString())
                .setClaims(map)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    @Override
    public HttpHeaders setHeader(String sessionToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_PARAM_NAME, sessionToken);
        logger.debug("Session token set in the http header - token={}", sessionToken);
        return headers;
    }

    @Override
    public void load(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_PARAM_NAME);
        if (hasLength(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            if (threadLocal == null) {
                threadLocal = new ThreadLocal<>();
            }
            threadLocal.set(claims);
            logger.debug("User session found in the request - claims=[{}]", claims);
        } else {
            logger.debug("No user session found for the request");
        }
    }

    @Override
    public String getStringValue(String key) {
        if (threadLocal != null) {
            return (String) threadLocal.get().get(key);
        }
        return null;
    }

    @Override
    public Long getUserId() {
        if (threadLocal != null) {
            return Long.valueOf((Integer) threadLocal.get().get("userId"));
        }
        return null;
    }

    @Override
    public void clear() {
        if (threadLocal != null) {
            threadLocal.remove();
        }
    }
}
