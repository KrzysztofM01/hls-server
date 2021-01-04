package com.sanesoft.hlsserver.service.audio.exception;

public class M3U8ReaderException extends Exception {

    public M3U8ReaderException() {
    }

    public M3U8ReaderException(String message) {
        super(message);
    }

    public M3U8ReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
