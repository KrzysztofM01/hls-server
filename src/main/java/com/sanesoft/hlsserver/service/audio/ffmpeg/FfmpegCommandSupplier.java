package com.sanesoft.hlsserver.service.audio.ffmpeg;

import java.util.List;

/**
 * Responsible for creating ffmpeg commands to create m3u8 metadata from audio file.
 *
 * @author kmirocha
 */
public interface FfmpegCommandSupplier {

    /**
     * Creates list of ffmpeg commands based on given parameters.
     *
     * @param pathToInputFile - path where input file is stored.
     * @param pathToOutputFile - path where output m3u8 metadata file should be stored.
     * @return list of commands to be run in ffmpeg.
     */
    List<String> getFfmpegCommands(String pathToInputFile, String pathToOutputFile);
}
