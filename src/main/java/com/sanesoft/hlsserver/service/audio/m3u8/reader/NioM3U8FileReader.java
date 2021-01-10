package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8ReaderException;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NioM3U8FileReader implements M3U8FileReader {

    private static final Pattern PART_LOCATION_PATTERN = Pattern.compile("^output([0-9]*)\\.ts$");

    private final M3U8FileReaderConfig config;

    public NioM3U8FileReader(M3U8FileReaderConfig config) {
        this.config = config;
    }

    @Override
    public String readEncodedM3U8File(Path pathToFile, String userName, String audioName) {
        try {
            return Files.readAllLines(pathToFile)
                    .stream()
                    .map(s -> {
                        Matcher matcher = PART_LOCATION_PATTERN.matcher(s);
                        if (matcher.find()) {
                            return UriComponentsBuilder.newInstance()
                                    .uri(config.getAudioFilePartURI())
                                    .pathSegment("users")
                                    .pathSegment(userName)
                                    .pathSegment("audio-files")
                                    .pathSegment(audioName)
                                    .pathSegment(matcher.group(1))
                                    .build()
                                    .toUriString();
                        }
                        return s;
                    })
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
