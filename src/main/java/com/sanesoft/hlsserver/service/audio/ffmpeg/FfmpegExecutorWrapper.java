package com.sanesoft.hlsserver.service.audio.ffmpeg;

import java.util.List;

/**
 * Wrapper responsible for executing supplied ffmpeg commands.
 *
 * @author kmirocha
 */
public interface FfmpegExecutorWrapper {

    /**
     * Executes supplied ffmpeg commands.
     *
     * @param args ffmpeg commands
     */
    void execute(List<String> args);
}
