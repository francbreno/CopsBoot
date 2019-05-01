package com.breno.copsboot;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.breno.copsboot.user.User;
import com.breno.copsboot.user.UserId;
import com.breno.copsboot.user.UserRepository;
import com.breno.copsboot.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	protected UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createOfficer(String email, String password) {
		User user = User.createOfficer(
				repository.nextId(),
				email,
				passwordEncoder.encode(password));
		return repository.save(user);
	}

	@Override
	public Optional<User> getUser(UserId userId) {
		return repository.findById(userId.getId());
	}

}
