package com.sanesoft.hlsserver.service.audio.exception;

import com.sanesoft.hlsserver.exception.HlsServerExceptionHandler;

/**
 * Base runtime exception that is thrown whenever one of the audio-based services fail. Added for convenient handling
 * in {@link HlsServerExceptionHandler}.
 *
 * @author kmirocha
 */
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
