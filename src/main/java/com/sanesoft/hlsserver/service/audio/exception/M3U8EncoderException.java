package com.sanesoft.hlsserver.service.audio.exception;

public class M3U8EncoderException extends Exception {

    public M3U8EncoderException() {
    }

    public M3U8EncoderException(String message) {
        super(message);
    }

    public M3U8EncoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
