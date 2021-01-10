package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.sanesoft.hlsserver.config.AudioFileConfig;
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
    public void storeM3U8Files(Path m3u8ParentDirectory) {
        // empty, the files are already created in proper directory and nothing should be done
    }
}
