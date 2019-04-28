package com.breno.copsboot.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.breno.copsboot.orm.jpa.InMemoryUniqueIdGenerator;
import com.breno.copsboot.orm.jpa.UniqueIdGenerator;

@RunWith(SpringRunner.class) // enable testing support of spring boot
@DataJpaTest // starts only the parts of the app related to JPA (test slicing)
public class UserRepositoryTest {
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		public UniqueIdGenerator<UUID> generator() {
			return new InMemoryUniqueIdGenerator();
		}
	}

	// Inside tests is not possible to define
	// injection with constructor
	@Autowired
	private UserRepository repository;

	@Test
	public void testStoreUser() {
		// Given
		HashSet<UserRole> roles = new HashSet<>();
		roles.add(UserRole.OFFICER);
		// When
		User user = repository.save(new User(
				repository.nextId(),
				"alex.foley@beverly-hills.com",
				"my-secret-pws",
				roles));
		// Then
		assertThat(user).isNotNull();
		assertThat(repository.count()).isEqualTo(1L);
	}

	@Test
	public void testFindByEmail() {
		// Given
		User user = Users.newRandomOfficer();
		repository.save(user);
		// When
		Optional<User> optional = repository.findByEmailIgnoreCase(user.getEmail());
		// Then
		assertThat(optional).isNotEmpty().contains(user);
	}
	
	@Test
	public void testFindByEmailIgnoringCase() {
		// Given
		User user = Users.newRandomOfficer();
		repository.save(user);
		// When
		Optional<User> optional = repository.findByEmailIgnoreCase(user.getEmail().toUpperCase());
		// Then
		assertThat(optional).isNotEmpty().contains(user);
	}
	
	@Test
	public void testFinByEmail_unknownEMail() {
		// Given
		User user = Users.newRandomOfficer();
		repository.save(user);
		// When
		Optional<User> optional = repository.findByEmailIgnoreCase("will.not@find.me");
		// Then
		assertThat(optional).isEmpty();
	}
}
