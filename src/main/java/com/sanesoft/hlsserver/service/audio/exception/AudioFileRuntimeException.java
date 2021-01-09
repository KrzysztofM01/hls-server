package com.sanesoft.hlsserver.service.audio.exception;

public class AudioFileRuntimeException extends RuntimeException {

    public AudioFileRuntimeException() {
    }

    public AudioFileRuntimeException(String message) {
        super(message);
    }

    public AudioFileRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
