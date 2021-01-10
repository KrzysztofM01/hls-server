package com.sanesoft.hlsserver.service.audio.reader;

import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class GcpAudioFileReader implements AudioFileReader {

    private final Storage storage;
    private final GcpStorageConfig config;

    @Override
    public byte[] readAudioPartFile(Path pathToAudioRootDirectory, Integer audioPathId) {
        return storage.get(
                config.getBucketName(),
                pathToAudioRootDirectory.resolve("output" + audioPathId + ".ts").toString()
        ).getContent();
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.GCP;
    }
}
