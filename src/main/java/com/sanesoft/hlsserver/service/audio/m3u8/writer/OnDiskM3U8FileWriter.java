package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class OnDiskM3U8FileWriter implements M3U8FileWriter {

    private final AudioFileConfig config;

    @Override
    public Path getPathWhereFileShouldBeStored() {
        return config.getRootAudioFileSavePath();
    }

    @Override
    public Path storeM3U8Files(Path m3u8OutputFile) {
        return m3u8OutputFile;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }
}
