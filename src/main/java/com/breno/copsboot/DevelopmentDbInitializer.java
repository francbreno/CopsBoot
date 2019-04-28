package com.breno.copsboot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.breno.copsboot.infrastructure.SpringProfiles;
import com.breno.copsboot.user.UserService;

@Component
@Profile(SpringProfiles.DEV)
public class DevelopmentDbInitializer implements ApplicationRunner {
	
	private final UserService userService;
	
	protected DevelopmentDbInitializer(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createTestUsers();
	}

	private void createTestUsers() {
		userService.createOfficer("officer@example.com", "officer");
	}
}
