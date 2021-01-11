package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8ReaderException;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class LocalM3U8FileReader implements M3U8FileReader {

    private final M3U8FileReaderConfig config;

    public LocalM3U8FileReader(M3U8FileReaderConfig config) {
        this.config = config;
    }

    @Override
    public String readEncodedM3U8File(Path pathToFile, String userName, String audioName) {
        try {
            return Files.readAllLines(pathToFile)
                    .stream()
                    .map(s -> M3U8OutputPartReplacerUtil.replaceOutputPart(
                            s, config.getAudioFilePartURI(), userName, audioName
                    ))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new M3U8ReaderException("Error while trying to read m3u8 file", e);
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }
}
