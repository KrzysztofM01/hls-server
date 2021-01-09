package com.sanesoft.hlsserver.config.typesafe;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypesafeAudioFileConfigTest {

    private final Config c = ConfigFactory.parseString(
            "root-audio-file-save-path = some-path-1\n" +
            "audio-part-duration = 5s\n" +
            "audio-bit-rate = 666\n" +
            "path-to-ffmpeg = some-path-2");
    private final AudioFileConfig config = new TypesafeAudioFileConfig(c);

    @Test
    void getRootAudioFileSavePath_returnsProperPath() {
        // when
        Path result = config.getRootAudioFileSavePath();

        // then
        assertEquals(Path.of("some-path-1"), result);
    }

    @Test
    void getAudioParthDuration_returnsProperDuration() {
        // when
        Duration result = config.getAudioPartDuration();

        // then
        assertEquals(Duration.ofSeconds(5), result);
    }

    @Test
    void getAudioBitRate_returnsProperInteger() {
        // when
        Integer result = config.getAudioBitRate();

        // then
        assertEquals(666, result);
    }

    @Test
    void getPathToFfmpeg_returnsProperPath() {
        // when
        String result = config.getPathToFFmpeg();

        // then
        assertEquals("some-path-2", result);
    }
}