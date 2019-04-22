package com.breno.copsboot.user;

import java.util.UUID;

import com.breno.copsboot.orm.jpa.AbstractEntityId;
import com.breno.copsboot.util.ArtifactForFramework;

public class UserId extends AbstractEntityId<UUID> {

	private static final long serialVersionUID = 1L;

	@ArtifactForFramework
	protected UserId() {
		
	}
	
	public UserId(UUID id) {
		super(id);
	}
}
