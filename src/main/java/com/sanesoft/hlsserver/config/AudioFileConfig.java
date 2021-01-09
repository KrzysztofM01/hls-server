package com.sanesoft.hlsserver.config;

import java.nio.file.Path;
import java.time.Duration;

public interface AudioFileConfig {

    Path getRootAudioFileSavePath();

    Duration getAudioPartDuration();

    Integer getAudioBitRate();

    String getPathToFFmpeg();
}
