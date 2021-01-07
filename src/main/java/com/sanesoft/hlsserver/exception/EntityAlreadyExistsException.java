package com.sanesoft.hlsserver.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String name) {
        super("Entity with given name already exists!");
    }
}
