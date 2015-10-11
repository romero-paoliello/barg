package com.barganha.service;


import com.barganha.model.User;

public interface EmailService {

	void sendResetPasswordEmail(User user);
	
}