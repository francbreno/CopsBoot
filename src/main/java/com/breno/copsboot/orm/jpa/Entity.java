package com.breno.copsboot.orm.jpa;

/**
 * Interface for entities objects.
 * 
 * @author breno
 *
 * @param <T> the type of {@link EntityId} that will
 * be used in this entity
 */
public interface Entity<T extends EntityId> {

	T getId();
}
