package com.barganha.service;

import com.barganha.dao.UserDao;
import com.barganha.model.data.RegistrationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.barganha.util.Messages.*;
import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasLength;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private UserDao userDao;

	@Override
	public void validateEmail(String email) {
		// TODO implement email validation
		if (false) {
			throw new IllegalArgumentException("app.validation.email.invalid");
		}
	}

	@Override
	public void validatePassword(String name, String password, String confirmPassword) {
		if (password.length() < 6) {
			throw new IllegalArgumentException("app.validation.password.too.short");
		}

		if (password.contains(name)) {
			throw new IllegalArgumentException("app.validation.password.contains.name");
		}

		if (!password.equals(confirmPassword)) {
			throw new IllegalArgumentException("app.validation.passwords.dont.match");
		}
	}

	@Override
	public void validateRegistration(RegistrationData rd) {
		checkArgument(rd != null, VALIDATION_REG_FORM_EMPTY);
		checkArgument(hasLength(rd.getName()), VALIDATION_NAME_EMPTY);
		checkArgument(rd.getName().length() > 10, VALIDATION_NAME_TOO_SHORT);
		checkArgument(hasLength(rd.getEmail()), VALIDATION_EMAIL_EMPTY);
		checkArgument(hasLength(rd.getPassword()), VALIDATION_PASSWORD_EMPTY);
		checkArgument(hasLength(rd.getConfirmPassword()), VALIDATION_CONFIRM_PASSWORD_EMPTY);

		validateEmail(rd.getEmail());
		validatePassword(rd.getName(), rd.getPassword(), rd.getConfirmPassword());
		if (userDao.findByEmail(rd.getEmail()) != null) {
			throw new IllegalArgumentException(VALIDATION_EMAIL_ALREADY_REGISTERED);
		}
	}

}
