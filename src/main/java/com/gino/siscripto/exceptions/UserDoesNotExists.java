package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class UserDoesNotExists extends ApiException {

    public UserDoesNotExists(String dni){
        super("User " + dni + " does not exists", HttpStatus.NOT_FOUND);

    }
}
