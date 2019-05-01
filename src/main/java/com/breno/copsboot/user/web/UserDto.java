package com.breno.copsboot.user.web;

import java.util.Set;

import com.breno.copsboot.user.User;
import com.breno.copsboot.user.UserId;
import com.breno.copsboot.user.UserRole;

import lombok.Value;

@Value
public class UserDto {

	private final UserId id;
	private final String email;
	private final Set<UserRole> roles;
	
	public static UserDto fromUser(User user) {
		return new UserDto(user.getId(),
						   user.getEmail(),
						   user.getRoles());
	}
}
