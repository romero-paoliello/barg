package com.barganha.barganha.api.filter;

import com.barganha.barganha.service.SessionService;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/secured/*")
public class JwtFilter extends GenericFilterBean {

    @Autowired private SessionService sessionService;

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {

        try {

            HttpServletRequest request = (HttpServletRequest) req;
            sessionService.load(request);
            chain.doFilter(req, res);

        } catch (SignatureException e) {
            throw new ServletException("Invalid session token.");
        } finally {
            sessionService.clear();
        }

    }

}
