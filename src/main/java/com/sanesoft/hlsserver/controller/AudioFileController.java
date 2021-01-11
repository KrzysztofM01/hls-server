package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.service.audio.AudioFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Responsible for handling requests and responses connected with {@link AudioFileService}.
 *
 * @author kmirocha
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userName}/audio-files")
public class AudioFileController {

    private final AudioFileService audioFileService;

    @PostMapping(value="/{audioName}", consumes = "multipart/form-data")
    ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                            @PathVariable String userName,
                                            @PathVariable String audioName) {
        try {
            audioFileService.saveAudioFile(userName, audioName, file.getInputStream());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Could not read provided file");
        }
    }

    @GetMapping(value = "/index/{audioName}", produces = "audio/mpegurl")
    ResponseEntity<String> getAudioMetadata(@PathVariable String userName,
                                       @PathVariable String audioName) {
        return ResponseEntity.ok(audioFileService.getAudioFileMetadata(userName, audioName));
    }

    @GetMapping(value = "/{audioName}/{audioPartId}", produces = "video/mp2t")
    ResponseEntity<byte[]> getAudioFilePart(@PathVariable String userName,
                                            @PathVariable String audioName,
                                            @PathVariable Integer audioPartId) {
        return ResponseEntity.ok(audioFileService.getAudioFile(userName, audioName, audioPartId));
    }
}
