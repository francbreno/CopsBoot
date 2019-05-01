package com.breno.copsboot.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Spring uses the status code in the response
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(UserId userId) {
		super(String.format("Could not find user with id %s", userId.asString()));
	}
}
