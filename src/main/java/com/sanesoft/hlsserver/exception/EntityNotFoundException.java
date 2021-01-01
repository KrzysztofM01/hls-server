package com.sanesoft.hlsserver.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String name) {
        super("Could not find entity with name: " + name);
    }
}
