package com.barganha.barganha.api;

import com.barganha.barganha.model.User;
import com.barganha.barganha.model.data.AccountData;
import com.barganha.barganha.model.data.ChangePasswordData;
import com.barganha.barganha.model.data.RegistrationData;
import com.barganha.barganha.model.data.ResetPasswordData;
import com.barganha.barganha.service.AccountService;
import com.barganha.barganha.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/open/account", method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody RegistrationData rd) {
        AccountData ad = accountService.create(rd);
        String sessionToken = sessionService.create(ad);
        HttpHeaders headers = sessionService.setHeader(sessionToken);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/secured/account", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody AccountData ad) {
        Long userIdFromSession = sessionService.getUserId();
        ad.setUserId(userIdFromSession);
        accountService.update(ad);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/secured/account/password", method = RequestMethod.PUT)
    public ResponseEntity changePassword(@RequestBody ChangePasswordData cpd) {
        Long userId = sessionService.getUserId();
        cpd.setUserId(userId);
        accountService.changePassword(cpd);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/open/account/password/send-reset-link", method = RequestMethod.PUT)
    public ResponseEntity sendResetPasswordEmail(@RequestParam("email") String email) {
        accountService.sendResetPasswordEmail(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/open/account/password/reset-password", method = RequestMethod.PUT)
    public ResponseEntity resetPassword(@RequestBody ResetPasswordData rpd) {
        accountService.resetPassword(rpd);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
