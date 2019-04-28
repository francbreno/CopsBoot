package com.breno.copsboot.user;

import java.util.Collections;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.breno.copsboot.orm.jpa.AbstractEntity;

@Entity
@Table(name = "copsboot_user")
public class User extends AbstractEntity<UserId> {
	
	private String email;
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Set<UserRole> roles;
	
	protected User() {
		super();
	}

	public User(UserId id, String email, String password, Set<UserRole> roles) {
		super(id);
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public static User createCaptain(UserId userId, String email, String password) {
        return new User(userId, email, password, Collections.singleton(UserRole.CAPTAIN));
    }

    public static User createOfficer(UserId userId, String email, String password) {
        return new User(userId, email, password, Collections.singleton(UserRole.OFFICER));
    }

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}
}
