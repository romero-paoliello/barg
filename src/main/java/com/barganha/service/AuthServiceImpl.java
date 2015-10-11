package com.barganha.service;

import com.barganha.dao.UserDao;
import com.barganha.model.User;
import com.barganha.model.data.AccountData;
import com.barganha.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasLength;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private CryptoService cryptoService;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AccountData login(String email, String password) {
        checkArgument(hasLength(email), Messages.VALIDATION_EMAIL_EMPTY);
        checkArgument(hasLength(password), Messages.VALIDATION_PASSWORD_EMPTY);

        logger.debug("About to login - email={}", email);

        password = cryptoService.crypt(password);
        User user = userDao.findByEmailAndPassword(email, password);
        if (user != null) {
            //user.setLastAccess(DateTimeHelper.getCurrentDateTime());
            logger.info("Logged in - email={}", email);
            return new AccountData(user);
        } else {
            logger.info("Login failed - email={}", email);
            // TODO implement login failed counter
            return null;
        }
    }

}
