package com.barganha.barganha.service;


import com.barganha.barganha.model.data.AccountData;

public interface AuthService {

	AccountData login(String email, String password);

}