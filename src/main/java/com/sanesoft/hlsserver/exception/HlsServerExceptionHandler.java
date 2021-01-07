package com.sanesoft.hlsserver.exception;

import com.sanesoft.hlsserver.service.audio.exception.AudioFileRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HlsServerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<String> entityNotFoundHandler(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EntityAlreadyExistsException.class)
    ResponseEntity<String> entityNotFoundHandler(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AudioFileRuntimeException.class)
    ResponseEntity<String> audioFileExceptionHandler(AudioFileRuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
