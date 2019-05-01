package com.breno.copsboot.user.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.breno.copsboot.infrastructure.security.ApplicationUserDetails;
import com.breno.copsboot.user.User;
import com.breno.copsboot.user.UserNotFoundException;
import com.breno.copsboot.user.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserService service;

	protected UserRestController(UserService service) {
		super();
		this.service = service;
	}

	@GetMapping("/me")
	public UserDto currentUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
		// This route is protected  by Spring Security, so userDetails is never null
		User user = service.getUser(userDetails.getUserId())
						   .orElseThrow(() -> new UserNotFoundException(userDetails.getUserId()));
		/*
		 * Using a DTO object instead of an entity. Some reasons:
		 * - Avoid putting annotations (like Jackson) inside the entities
		 * - The object returned needs to format data differently, hide some data or add extra info. 
		 */
		return UserDto.fromUser(user);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	/**
	 * Desserialize the request body JSON data using Jackson
	 * @param parameters
	 * @return
	 */
	public UserDto createOfficer(@Valid @RequestBody CreateOfficerParameters parameters) {
		User officer = service.createOfficer(
				parameters.getEmail(), 
				parameters.getPassword());
		
		return UserDto.fromUser(officer);
	}
}
