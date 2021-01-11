package com.sanesoft.hlsserver.service.audio.storage;

import com.sanesoft.hlsserver.service.audio.m3u8.reader.M3U8FileReader;
import com.sanesoft.hlsserver.service.audio.m3u8.writer.M3U8FileWriter;
import com.sanesoft.hlsserver.service.audio.reader.AudioFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StorageTypeConfiguration {

    private final List<AudioFileReader> audioFileReaders;
    private final List<M3U8FileWriter> m3U8FileWriters;
    private final List<M3U8FileReader> m3U8FileReaders;
    @Value("${storage.type}")
    private StorageType type;

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
                .filter(s -> s.getStorageType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no proper interface implementation for type: " + type));
    }
}
