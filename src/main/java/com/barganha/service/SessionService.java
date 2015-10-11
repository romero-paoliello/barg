package com.barganha.service;

import com.barganha.model.data.AccountData;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {

    boolean isLoggedIn();

    HttpHeaders setHeader(String sessionToken);

    String create(AccountData ad);

    void load(HttpServletRequest request);

    void clear();

    Long getUserId();

    String getStringValue(String key);
}
