package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * Responsible for writing m3u8 metadata file along with audio chunks to local storage.
 *
 * @author kmirocha
 */
@RequiredArgsConstructor
@Service
public class LocalM3U8FileWriter implements M3U8FileWriter {

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
