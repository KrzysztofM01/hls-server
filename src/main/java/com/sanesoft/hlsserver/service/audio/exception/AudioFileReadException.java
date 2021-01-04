package com.sanesoft.hlsserver.service.audio.exception;

public class AudioFileReadException extends Exception {

    public AudioFileReadException() {
    }

    public AudioFileReadException(String message) {
        super(message);
    }

    public AudioFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
