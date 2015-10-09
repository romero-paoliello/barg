package com.barganha.barganha.service;


import com.barganha.barganha.model.User;

public interface EmailService {

	void sendResetPasswordEmail(User user);
	
}