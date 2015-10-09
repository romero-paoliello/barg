package com.barganha.barganha.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class StatusController {

    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity status() {
        logger.debug("OK");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
