package com.sanesoft.hlsserver.service.audio.reader;

import com.sanesoft.hlsserver.service.audio.exception.AudioFileReadException;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalAudioFileReader implements AudioFileReader {

    @Override
    public byte[] readAudioPartFile(Path pathToAudioRootDirectory, Integer audioPathId) {
        try {
            return Files.readAllBytes(pathToAudioRootDirectory
                    .resolve("output" + audioPathId + ".ts"));
        } catch (IOException e) {
            throw new AudioFileReadException("Error while trying to read audio file", e);
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }
}
