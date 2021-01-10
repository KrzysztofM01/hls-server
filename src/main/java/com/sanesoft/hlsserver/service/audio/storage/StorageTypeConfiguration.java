package com.sanesoft.hlsserver.service.audio.storage;

import com.sanesoft.hlsserver.service.audio.m3u8.reader.M3U8FileReader;
import com.sanesoft.hlsserver.service.audio.m3u8.writer.M3U8FileWriter;
import com.sanesoft.hlsserver.service.audio.reader.AudioFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageTypeConfiguration { //TODO fix this class

    @Value("${storage.type}")
    private final String type; //TODO
    private final List<AudioFileReader> audioFileReaders;
    private final List<M3U8FileWriter> m3U8FileWriters;
    private final List<M3U8FileReader> m3U8FileReaders;

    @Bean
    AudioFileReader audioFileReader() {
        return getProperInterface(audioFileReaders);
    }

    @Bean
    M3U8FileWriter m3U8FileWriter() {
        return getProperInterface(m3U8FileWriters);
    }

    @Bean
    M3U8FileReader m3U8FileReader() {
        return getProperInterface(m3U8FileReaders);
    }

    private <T extends StorageTypeBasedInterface> T getProperInterface(List<T> list) {
        return list.stream()
                .filter(s -> s.getStorageType().name().equals(type)) // TODO
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("TODO")); // TODO
    }
}
