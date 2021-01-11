package com.sanesoft.hlsserver.service.audio.exception;

/**
 * Exception thrown when request audio file cannot be read.
 *
 * @author kmirocha
 */
public class AudioFileReadException extends AudioFileRuntimeException {

    public AudioFileReadException() {
    }

    public AudioFileReadException(String message) {
        super(message);
    }

    public AudioFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
