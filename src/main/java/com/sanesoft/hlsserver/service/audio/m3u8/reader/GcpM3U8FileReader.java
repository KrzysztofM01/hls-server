package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GcpM3U8FileReader implements M3U8FileReader { //todo copy of code, fix this

    private static final Pattern PART_LOCATION_PATTERN = Pattern.compile("^output([0-9]*)\\.ts$");

    private final M3U8FileReaderConfig config;
    private final GcpStorageConfig storageConfig;
    private final Storage storage;

    @Override
    public String readEncodedM3U8File(Path pathToFile, String userName, String audioName) {
        return new String(storage.get(
                storageConfig.getBucketName(),
                pathToFile.toString()
        ).getContent()).lines()
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
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.GCP;
    }
}
