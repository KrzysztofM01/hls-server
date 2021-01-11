package com.sanesoft.hlsserver.config;

import java.nio.file.Path;
import java.time.Duration;

/**
 * Config responsible for handling audio files.
 *
 * @author kmirocha
 */
public interface AudioFileConfig {

    /**
     * @return root path where uploaded audio files will be stored.
     */
    Path getRootAudioFileSavePath();

    /**
     * @return duration to which uploaded audio files will be cut into.
     */
    Duration getAudioPartDuration();

    /**
     * @return bit rate to which uploaded audio will be converted to.
     */
    Integer getAudioBitRate();

    /**
     * @return path to ffmpeg executables.
     */
    String getPathToFFmpeg();
}
