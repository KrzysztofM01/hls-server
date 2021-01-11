package com.sanesoft.hlsserver.exception;

/**
 * Exception thrown when requested entity was not found in database.
 *
 * @author kmirocha
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String name) {
        super("Could not find entity with name: " + name);
    }
}
