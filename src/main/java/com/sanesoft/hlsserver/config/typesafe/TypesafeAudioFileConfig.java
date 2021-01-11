package com.sanesoft.hlsserver.config.typesafe;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.typesafe.config.Config;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Duration;

/**
 * Typesafe implementation of {@link AudioFileConfig}.
 *
 * @author kmirocha
 */
@Component
public class TypesafeAudioFileConfig implements AudioFileConfig {

    private static final String ROOT_AUDIO_PATH = "root-audio-file-save-path";
    private static final String AUDIO_PART_DURATION = "audio-part-duration";
    private static final String AUDIO_BIT_RATE = "audio-bit-rate";
    private static final String PATH_TO_FFMPEG = "path-to-ffmpeg";

    private final Config config;

    public TypesafeAudioFileConfig(Config config) {
        this.config = config;
    }

    @Override
    public Path getRootAudioFileSavePath() {
        return Path.of(config.getString(ROOT_AUDIO_PATH));
    }

    @Override
    public Duration getAudioPartDuration() {
        return config.getDuration(AUDIO_PART_DURATION);
    }

    @Override
    public Integer getAudioBitRate() {
        return config.getInt(AUDIO_BIT_RATE);
    }

    @Override
    public String getPathToFFmpeg() {
        return config.getString(PATH_TO_FFMPEG);
    }
}
