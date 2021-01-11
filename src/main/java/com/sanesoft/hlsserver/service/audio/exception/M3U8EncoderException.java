package com.sanesoft.hlsserver.service.audio.exception;

/**
 * Exception thrown when server was unable to encode given audio file to m3u8 format.
 *
 * @author kmirocha
 */
public class M3U8EncoderException extends AudioFileRuntimeException {

    public M3U8EncoderException() {
    }

    public M3U8EncoderException(String message) {
        super(message);
    }

    public M3U8EncoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
