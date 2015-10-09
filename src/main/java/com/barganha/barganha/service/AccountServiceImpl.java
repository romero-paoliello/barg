package com.barganha.barganha.service;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasLength;
import static com.barganha.barganha.util.Messages.*;

import com.barganha.barganha.api.AccountController;
import com.barganha.barganha.util.DateTimeHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barganha.barganha.dao.UserDao;
import com.barganha.barganha.model.User;
import com.barganha.barganha.model.data.AccountData;
import com.barganha.barganha.model.data.ChangePasswordData;
import com.barganha.barganha.model.data.RegistrationData;
import com.barganha.barganha.model.data.ResetPasswordData;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired private CryptoService cryptoService;
	@Autowired private EmailService emailService;
	@Autowired private ValidationService validationService;
	@Autowired private UserDao userDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AccountData create(RegistrationData rd) {
		validationService.validateRegistration(rd);

        logger.debug("About to created account - [{}]", rd);

		User user = new User(rd);
		user.setEmail(rd.getEmail());
		user.setPassword(cryptoService.crypt(rd.getPassword()));
		user.setName(rd.getName());
		user.setCreatedOn(DateTimeHelper.getCurrentDateTime());
		user = userDao.save(user);

        logger.info("Account created - [{}]", user);

		// TODO create event to send welcome email

		return new AccountData(user);
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AccountData update(AccountData ad) {
        checkArgument(ad != null, VALIDATION_ACCOUNT_DATA_EMPTY);
        checkArgument(hasLength(ad.getName()), VALIDATION_NAME_EMPTY);
        checkArgument(hasLength(ad.getEmail()), VALIDATION_EMAIL_EMPTY);

        logger.debug("About to update account - [{}]", ad);

		User user = userDao.findById(ad.getUserId());
		user.setEmail(ad.getEmail());
		user.setName(ad.getName());
		user.setLastUpdatedOn(DateTimeHelper.getCurrentDateTime());
        user = userDao.save(user);

        logger.info("Account updated - [{}]", user);

        return new AccountData(user);
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changePassword(ChangePasswordData cpd) {
        checkArgument(cpd != null, VALIDATION_ACCOUNT_DATA_EMPTY);
        checkArgument(hasLength(cpd.getCurrentPassword()), VALIDATION_CURRENT_PASSWORD_EMPTY);
        checkArgument(hasLength(cpd.getPassword()), VALIDATION_PASSWORD_EMPTY);
        checkArgument(hasLength(cpd.getConfirmPassword()), VALIDATION_CONFIRM_PASSWORD_EMPTY);

        logger.debug("About to change password - [{}]", cpd);

		User user = userDao.findById(cpd.getUserId());
		validationService.validatePassword(user.getName(), cpd.getPassword(), cpd.getConfirmPassword());
		user.setPassword(cryptoService.crypt(cpd.getPassword()));
        user.setLastUpdatedOn(DateTimeHelper.getCurrentDateTime());

        logger.info("Password changed - id={}, email={}", user.getId(), user.getEmail());

        userDao.save(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void sendResetPasswordEmail(String email) {
		checkArgument(hasLength(email), VALIDATION_EMAIL_EMPTY);

        logger.debug("About to send reset password email - email={}", email);

		User user = userDao.findByEmail(email);

        if (user != null) {
            if (user.getResetPasswordToken() == null) {
                String randomString = UUID.randomUUID().toString();
                String signedRandomString = cryptoService.crypt(randomString);
                user.setResetPasswordToken(signedRandomString);
                // TODO token should be valid for a predefined length of time
                user = userDao.save(user);
            }
            emailService.sendResetPasswordEmail(user);
            logger.info("Sent reset password email - id={}, email={}", user.getId(), user.getEmail());
        }
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void resetPassword(ResetPasswordData rpd) {
        checkArgument(hasLength(rpd.getEmail()), VALIDATION_EMAIL_EMPTY);
        checkArgument(hasLength(rpd.getPassword()), VALIDATION_PASSWORD_EMPTY);
        checkArgument(hasLength(rpd.getConfirmPassword()), VALIDATION_CONFIRM_PASSWORD_EMPTY);
        checkArgument(hasLength(rpd.getResetPasswordToken()), VALIDATION_INVALID_RESET_PASSWORD_TOKEN);

		User user = userDao.findByResetPasswordToken(rpd.getResetPasswordToken());
		if (user != null) {
            logger.debug("About to reset password - [{}]", rpd);

			validationService.validatePassword(user.getName(), rpd.getPassword(), rpd.getConfirmPassword());
			user.setPassword(cryptoService.crypt(rpd.getPassword()));
			user.setResetPasswordToken(null);
            userDao.save(user);
            logger.info("Password resetted - id={}, email={}", user.getId(), user.getEmail());
		} else {
			throw new IllegalArgumentException(VALIDATION_INVALID_RESET_PASSWORD_TOKEN);
		}
	}
}
