package com.sanesoft.hlsserver.service.audio.ffmpeg;

import java.util.List;

public interface FfmpegExecutorWrapper {

    void execute(List<String> args);
}
