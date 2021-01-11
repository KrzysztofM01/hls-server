package com.sanesoft.hlsserver.service.audio.exception;

/**
 * Exception thrown when server was unable to read m3u8 metadata file.
 *
 * @author kmirocha
 */
public class M3U8ReaderException extends AudioFileRuntimeException {

    public M3U8ReaderException() {
    }

    public M3U8ReaderException(String message) {
        super(message);
    }

    public M3U8ReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
