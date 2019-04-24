package com.breno.copsboot.orm.jpa;

public interface UniqueIdGenerator<T> {

	T getNextUniqueId();
}
