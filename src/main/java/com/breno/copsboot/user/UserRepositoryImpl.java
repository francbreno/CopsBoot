package com.breno.copsboot.user;

import java.util.UUID;

import com.breno.copsboot.orm.jpa.UniqueIdGenerator;

public class UserRepositoryImpl implements UserRepositoryCustom {
	private final UniqueIdGenerator<UUID> generator;
	
	protected UserRepositoryImpl(UniqueIdGenerator<UUID> generator) {
		super();
		this.generator = generator;
	}

	@Override
	public UserId nextId() {
		return new UserId(generator.getNextUniqueId());
	}

}
