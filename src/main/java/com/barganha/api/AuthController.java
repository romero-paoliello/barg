package com.barganha.api;

import com.barganha.model.data.AccountData;
import com.barganha.model.data.LoginData;
import com.barganha.service.AuthService;
import com.barganha.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/open/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginData ld) {
        AccountData ad = authService.login(ld.getEmail(), ld.getPassword());

        if (ad != null) {
            String sessionToken = sessionService.create(ad);

            HttpHeaders headers = sessionService.setHeader(sessionToken);
            return new ResponseEntity<>(ad, headers, HttpStatus.OK);
        } else {
            //Message message = new Message(MessageType.ERROR, "Login failed", "Login failed, please try again.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/open/facebooklogin", method = RequestMethod.POST)
    public ResponseEntity facebooklogin(@RequestBody LoginData ld) {
        AccountData ad = authService.facebooklogin(ld.getEmail());

        if (ad != null) {
            String sessionToken = sessionService.create(ad);

            HttpHeaders headers = sessionService.setHeader(sessionToken);
            return new ResponseEntity<>(ad, headers, HttpStatus.OK);
        } else {
            //Message message = new Message(MessageType.ERROR, "Login failed", "Login failed, please try again.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/secured/logout", method = RequestMethod.PUT)
    public ResponseEntity logout() {
        sessionService.clear(); // TODO think about the best way to implement this using JWT - consider timeout
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
