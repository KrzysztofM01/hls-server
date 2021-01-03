package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.repository.AudioFileRepository;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.MediaSegment;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AudioFileController {

    private final AudioFileRepository audioRepository;
    private final UserRepository userRepository;
    @Value("${audioFileSavePath}")
    private String audioFileSavePath;


    @PostMapping("/audio-files/{user}/{audioName}")
    ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file,
                                          @PathVariable("user") String userName,
                                          @PathVariable("audioName") String audioName) {
        return userRepository.findByName(userName)
                .map(user -> {
                    try {
                        Path pathToFile = Path.of(audioFileSavePath).resolve(userName).resolve(audioName);
                        Files.createDirectories(pathToFile.getParent());
                        Files.write(pathToFile, file.getBytes());


                        audioRepository.save(AudioFileInfo.builder()
                                .name(audioName)
                                .user(user)
                                .pathToFile(pathToFile.toAbsolutePath().toString())
                                .build());
                        return ResponseEntity.ok().build();
                    } catch (IOException e) {
                        log.error("Error while trying to save the audio file", e);
                        return ResponseEntity.status(500).build();
                    }
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/users/{userName}/audio-files/index/{audioName}", produces = "audio/mpegurl")
    String getAudioIndex(@PathVariable String userName,
                         @PathVariable String audioName) {
        MediaPlaylist mediaPlaylist = MediaPlaylist.builder()
                .version(3)
                .targetDuration(30)
                .mediaSequence(1)
                .ongoing(false)
                .addMediaSegments(
                        MediaSegment.builder()
                                .duration(10.067)
                                .uri("http://localhost:8080/users/testxx/audio-files/part1.wav")
                                .build(),
                        MediaSegment.builder()
                                .duration(10.359)
                                .uri("http://localhost:8080/users/testxx/audio-files/part2.wav")
                                .build(),
                        MediaSegment.builder()
                                .duration(10.359)
                                .uri("http://localhost:8080/users/testxx/audio-files/part3.wav")
                                .build())
                .build();

//        MasterPlaylist playlist = MasterPlaylist.builder()
//                .version(4)
//                .independentSegments(true)
//                .addVariants(
//                        Variant.builder()
//                                .addCodecs("avc1.4d401f", "mp4a.40.2")
//                                .bandwidth(900000)
//                                .uri("v0.m3u8")
//                                .build(),
//                        Variant.builder()
//                                .addCodecs("avc1.4d401f", "mp4a.40.2")
//                                .bandwidth(900000)
//                                .uri("v1.m3u8")
//                                .resolution(1280, 720)
//                                .build())
//                .build();

//        MasterPlaylistParser parser = new MasterPlaylistParser();
//        return parser.writePlaylistAsString(playlist);
        MediaPlaylistParser parser = new MediaPlaylistParser();
        return parser.writePlaylistAsString(mediaPlaylist);
    }

    @GetMapping(value = "/users/{userName}/audio-files/{audioName}", produces = "audio/wav")
    ResponseEntity<byte[]> getAudioFilePart(@PathVariable String userName,
                            @PathVariable String audioName) {
        Path pathToFile = Path.of(audioFileSavePath).resolve(userName).resolve(audioName);
        try {
            return ResponseEntity.ok(Files.readAllBytes(pathToFile));
        } catch (IOException e) {
            log.error("Error while trying to read file", e);
            return ResponseEntity.status(500).build();
        }
    }

//
//    @PostMapping("/user/{id}/audioFiles")
//    AudioFileInfo newAudioFile(@RequestBody AudioFileInfo newAudioFileInfo) {
//        return audioRepository.save(newAudioFileInfo);
//    }
//
//    @GetMapping("/AudioFiles/{id}")
//    AudioFileInfo one(@PathVariable Long id) {
//
//        return audioRepository.findById(id)
//                .orElseThrow(() -> new AudioFileNotFoundException(id));
//    }
//
//    @PutMapping("/AudioFiles/{id}")
//    AudioFileInfo replaceAudioFile(@RequestBody AudioFileInfo newAudioFileInfo, @PathVariable Long id) {
//
//        return audioRepository.findById(id)
//                .map(AudioFileInfo -> {
//                    AudioFileInfo.setName(newAudioFileInfo.getName());
//                    AudioFileInfo.setRole(newAudioFileInfo.getRole());
//                    return audioRepository.save(AudioFileInfo);
//                })
//                .orElseGet(() -> {
//                    newAudioFileInfo.setId(id);
//                    return audioRepository.save(newAudioFileInfo);
//                });
//    }
//
//    @DeleteMapping("/AudioFiles/{id}")
//    void deleteAudioFile(@PathVariable Long id) {
//        audioRepository.deleteById(id);
//    }
}
