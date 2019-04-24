package com.breno.copsboot.orm.jpa;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import com.breno.copsboot.util.ArtifactForFramework;

/**
 * Abstract super class for entities. We are assuming that early primary key
 * generation will be used.
 * 
 * @author breno
 *
 * @param <T> the type of {@link EntityId} that will be used for this entity
 */
@MappedSuperclass
public class AbstractEntity<T extends EntityId> implements Entity<T> {

	@EmbeddedId
	private T id;

	@ArtifactForFramework
	protected AbstractEntity() {
		super();
	}

	protected AbstractEntity(T id) {
		super();
		this.id = id;
	}

	public T getId() {
		return id;
	}
	
	/**
	 * equals is true only if they are the same reference
	 * of are of the type of this class and ids are equals.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if(this == obj) {
			result = true;
		} else if(obj instanceof AbstractEntity) {
			AbstractEntity other = (AbstractEntity) obj;
			result = Objects.equals(id, other.id);
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + "]";
	}
}
