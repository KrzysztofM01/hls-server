package com.sanesoft.hlsserver.controller;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.repository.AudioFileRepository;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @Value("${doopa}")
    private String audioFileSavePath;


    @PostMapping("/audio-files/{user}/{audioName}")
    ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file,
                                          @PathVariable("user") String userName,
                                          @PathVariable("audioName") String audioName) {
        try {
            log.info("file:{}, user:{}, audioName:{}", file.getBytes(), userName, audioName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userRepository.findByName(userName)
                .map(user -> {
                    log.info("User: {}", user);
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
                        log.error("Cos sie zjebało", e);
                        return ResponseEntity.status(500).build();
                    }
                })
                .orElse(ResponseEntity.badRequest().build());
    }

//    @GetMapping("/user/{id}/audioFiles")
//    List<AudioFileInfo> all() {
//        return audioRepository.findAll();
//    }
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
