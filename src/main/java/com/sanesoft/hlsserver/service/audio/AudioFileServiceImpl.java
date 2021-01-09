package com.sanesoft.hlsserver.service.audio;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.repository.AudioFileInfoRepository;
import com.sanesoft.hlsserver.database.repository.UserRepository;
import com.sanesoft.hlsserver.exception.EntityAlreadyExistsException;
import com.sanesoft.hlsserver.exception.EntityNotFoundException;
import com.sanesoft.hlsserver.service.audio.m3u8.encoder.M3U8Encoder;
import com.sanesoft.hlsserver.service.audio.m3u8.reader.M3U8FileReader;
import com.sanesoft.hlsserver.service.audio.reader.AudioFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class AudioFileServiceImpl implements AudioFileService {

    private final AudioFileInfoRepository audioRepository;
    private final UserRepository userRepository;
    private final M3U8Encoder m3U8Encoder;
    private final M3U8FileReader m3U8FileReader;
    private final AudioFileReader audioFileReader;

    @Override
    public void saveAudioFile(String userName, String audioName, InputStream inputStream) {
        // TODO add handling situation when we want to overwrite certain audio file
        audioRepository.findByNameAndUserName(audioName, userName)
                .ifPresentOrElse(
                        s -> {
                            throw new EntityAlreadyExistsException(audioName);
                        },
                        () -> userRepository.findByName(userName)
                                .ifPresentOrElse(user -> {
                                    Path path = m3U8Encoder.encodeFileToM3UFormat(
                                            user.getName(), audioName, inputStream
                                    );
                                    audioRepository.save(AudioFileInfo.builder()
                                            .user(user)
                                            .name(audioName)
                                            .pathToFile(path)
                                            .build());
                                }, () -> {
                                    throw new EntityNotFoundException(userName);
                                })
                );
    }

    @Override
    public String getAudioFileIndex(String userName, String audioName) {
        return audioRepository.findByNameAndUserName(audioName, userName)
                .map(AudioFileInfo::getPathToFile)
                .map(s -> m3U8FileReader.readEncodedM3U8File(s, userName, audioName))
                .orElseThrow(() -> new EntityNotFoundException("There is no such audio file."));
    }

    @Override
    public byte[] getAudioFile(String userName, String audioName, Integer audioPartId) {
        // TODO reading from database is not optimal, it would be best to create short-lived memory cache that
        //  would remember userName+audioName as key and path to the directory where audio chunks are stored.
        return audioRepository.findByNameAndUserName(audioName, userName)
                .map(AudioFileInfo::getPathToFile)
                .map(s -> audioFileReader.readAudioPartFile(s.getParent(), audioPartId))
                .orElseThrow(() -> new EntityNotFoundException("There is no such audio file."));
    }
}
