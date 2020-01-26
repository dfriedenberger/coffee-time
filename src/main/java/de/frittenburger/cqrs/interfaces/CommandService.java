package de.frittenburger.cqrs.interfaces;


public interface CommandService<E> {

	void update(E evnt);

}
