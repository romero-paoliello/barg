package com.barganha.service;


import com.barganha.model.data.AccountData;

public interface AuthService {

	AccountData login(String email, String password);

	AccountData facebooklogin(String email);
}