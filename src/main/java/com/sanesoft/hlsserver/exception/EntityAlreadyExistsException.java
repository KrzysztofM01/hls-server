package com.sanesoft.hlsserver.exception;

/**
 * Exception thrown when supplied data is already stored in database.
 *
 * @author kmirocha
 */
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String name) {
        super("Entity with given name already exists!");
    }
}
