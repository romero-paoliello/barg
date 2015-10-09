package com.barganha.barganha.service;

import com.barganha.barganha.model.data.AccountData;
import com.barganha.barganha.model.data.ChangePasswordData;
import com.barganha.barganha.model.data.RegistrationData;
import com.barganha.barganha.model.data.ResetPasswordData;

public interface AccountService {

	AccountData create(RegistrationData rd);

	AccountData update(AccountData ad);

	void changePassword(ChangePasswordData cpd);
	
	void sendResetPasswordEmail(String email);
	
	void resetPassword(ResetPasswordData rpd);

}