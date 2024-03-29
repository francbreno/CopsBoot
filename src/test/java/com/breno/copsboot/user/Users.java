package com.breno.copsboot.user;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Users {

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	public static final String OFFICER_EMAIL = "officer@example.com";
	public static final String OFFICER_PASSWORD = "officer";
	public static final String CAPTAIN_EMAIL = "captain@example.com";
	public static final String CAPTAIN_PASSWORD = "captain";
	
	private static final User OFFICER = User.createOfficer(
			newRandomId(),
			OFFICER_EMAIL,
			PASSWORD_ENCODER.encode(OFFICER_PASSWORD));
	
	private static final User CAPTAIN = User.createCaptain(
			newRandomId(),
			CAPTAIN_EMAIL,
			PASSWORD_ENCODER.encode(CAPTAIN_PASSWORD));
	
	public static UserId newRandomId() {
		return new UserId(UUID.randomUUID());
	}
	
	public static User newRandomOfficer() {
		return newRandomOfficer(newRandomId());
	}
	
	public static User newRandomOfficer(UserId userId) {
		String uniqueId = userId.asString().substring(0, 5);
		return User.createOfficer(userId,
				"user-" + uniqueId + "@example.com",
				PASSWORD_ENCODER.encode("user"));
	}
	
	public static User officer() {
        return OFFICER;
    }

    public static User captain() {
        return CAPTAIN;
    }

    public static User newOfficer(String email, String password) {
        return User.createOfficer(newRandomId(), email, PASSWORD_ENCODER.encode(password));
    }

    private Users() {

    }
}
