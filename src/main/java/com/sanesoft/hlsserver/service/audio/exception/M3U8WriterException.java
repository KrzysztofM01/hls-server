package com.sanesoft.hlsserver.service.audio.exception;

/**
 * Exception thrown when server was unable to write m3u8 file.
 *
 * @author kmirocha
 */
public class M3U8WriterException extends AudioFileRuntimeException {

    public M3U8WriterException() {
    }

    public M3U8WriterException(String message) {
        super(message);
    }

    public M3U8WriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
