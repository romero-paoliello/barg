package com.barganha.service;

import com.barganha.model.data.RegistrationData;

public interface ValidationService {

	void validateEmail(String email);

	void validatePassword(String name, String password, String confirmPassword);

	void validateRegistration(RegistrationData rd);

}