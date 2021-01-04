package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.repository.AudioFileRepository;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.service.audio.exception.M3U8ReaderException;
import com.sanesoft.hlsserver.service.audio.m3u8.encoder.M3U8Encoder;
import com.sanesoft.hlsserver.service.audio.m3u8.reader.M3U8FileReader;
import com.sanesoft.hlsserver.service.audio.exception.AudioFileReadException;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;
import com.sanesoft.hlsserver.service.audio.reader.AudioFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AudioFileController {

    private final AudioFileRepository audioRepository;
    private final UserRepository userRepository;
    private final M3U8Encoder m3U8Encoder;
    private final M3U8FileReader m3U8FileReader;
    private final AudioFileReader audioFileReader;


    @PostMapping("/users/{userName}/audio-files/{audioName}")
    ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                            @PathVariable String userName,
                                            @PathVariable String audioName) {
        // TODO add handling situation when we want to overwrite certain audio file
        return audioRepository.findByNameAndUserName(audioName, userName)
                .map(s -> ResponseEntity.badRequest().body("Audio with this name is already uploaded."))
                .or(() -> userRepository.findByName(userName)
                        .map(user -> {
                            try {
                                Path path = m3U8Encoder.encodeFileToM3UFormat(user, audioName, file.getInputStream());
                                audioRepository.save(AudioFileInfo.builder()
                                        .user(user)
                                        .name(audioName)
                                        .pathToFile(path)
                                        .build());
                                return ResponseEntity.ok().build();
                            } catch (IOException | M3U8EncoderException e) {
                                log.error("Error while trying to save the audio file", e);
                                return ResponseEntity.status(500).body("");
                            }
                        })
                )
                .orElse(ResponseEntity.badRequest().body("User with this name does not exist."));
    }

    @GetMapping(value = "/users/{userName}/audio-files/index/{audioName}", produces = "audio/mpegurl")
    ResponseEntity<String> getAudioIndex(@PathVariable String userName,
                                       @PathVariable String audioName) {
//        return audioRepository.findByNameAndUserName(userName, audioName) //TODO fix this query
        return audioRepository.findByName(audioName)
                .map(AudioFileInfo::getPathToFile)
                .map(s -> {
                    try {
                        return ResponseEntity.ok(m3U8FileReader.readEncodedM3U8File(s, userName, audioName));
                    } catch (M3U8ReaderException e) {
                        return ResponseEntity.status(500).body("Error while trying to read m3u8 file");
                    }
                })
                .orElse(ResponseEntity.badRequest().body("Audio file not found."));
    }

    @GetMapping(value = "/users/{userName}/audio-files/{audioName}/{audioPartId}", produces = "video/mp2t")
    ResponseEntity<?> getAudioFilePart(@PathVariable String userName,
                                            @PathVariable String audioName,
                                            @PathVariable Integer audioPartId) {
        // TODO reading from database is not optimal, it would be best to create short-lived memory cache that
        //  would remember userName+audioName as key and path to the directory where audio chunks are stored.
//        return audioRepository.findByNameAndUserName(userName, audioName) // TODO fix this query
        return audioRepository.findByName(audioName)
                .map(AudioFileInfo::getPathToFile)
                .map(s -> {
                    try {
                        return ResponseEntity.ok(audioFileReader.readAudioPartFile(s.getParent(), audioPartId));
                    } catch (AudioFileReadException e) {
                        return ResponseEntity.status(500).body("Error while trying to read audio file");
                    }
                })
                .orElse(ResponseEntity.badRequest().body("Audio file not found."));
    }
}
