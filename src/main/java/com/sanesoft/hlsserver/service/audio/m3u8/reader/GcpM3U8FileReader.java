package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GcpM3U8FileReader implements M3U8FileReader {

    private final M3U8FileReaderConfig config;
    private final GcpStorageConfig storageConfig;
    private final Storage storage;

    @Override
    public String readEncodedM3U8File(Path pathToFile, String userName, String audioName) {
        return new String(storage.get(
                storageConfig.getBucketName(),
                pathToFile.toString()
        ).getContent()).lines()
                .map(s -> M3U8OutputPartReplacerUtil.replaceOutputPart(
                        s, config.getAudioFilePartURI(), userName, audioName
                ))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.GCP;
    }
}
