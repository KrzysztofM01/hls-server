package com.sanesoft.hlsserver.config.typesafe;

import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.typesafe.config.Config;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class TypesafeM3U8FileReaderConfig implements M3U8FileReaderConfig {

    private static final String AUDIO_FILE_PART_URI = "audio-file-part-uri";

    private final Config config;

    public TypesafeM3U8FileReaderConfig(Config config) {
        this.config = config;
    }

    @Override
    public URI getAudioFilePartURI() {
        return URI.create(config.getString(AUDIO_FILE_PART_URI));
    }
}
