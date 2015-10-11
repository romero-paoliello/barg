package com.barganha.api.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
@WebFilter(urlPatterns = "/*")
public class LogFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String mdcId = request.getParameter("mdcId");

        if (mdcId == null) {
            mdcId = UUID.randomUUID().toString();
        }

        MDC.put("UUID", mdcId);

        chain.doFilter(req, res);

    }

}
