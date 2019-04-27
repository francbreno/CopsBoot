package com.breno.copsboot.orm.jpa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

import com.breno.copsboot.util.ArtifactForFramework;

@MappedSuperclass
public abstract class AbstractEntityId<T extends Serializable> implements Serializable, EntityId<T> {
	private static final long serialVersionUID = 1L;

	private T id;

	@ArtifactForFramework
	protected AbstractEntityId() {
		super();
	}

	protected AbstractEntityId(T id) {
		super();
		this.id = Objects.requireNonNull(id);
	}

	public T getId() {
		return id;
	}
	
	@Override
	public String asString() {
		return id.toString();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		
		if (this == o) {
			result = true;
		} else if (o instanceof AbstractEntityId){
			AbstractEntityId other = (AbstractEntityId) o;
			result = Objects.equals(id,  other.id);
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "AbstractEntityId [id=" + id + "]";
	}
}
