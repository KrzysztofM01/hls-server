package com.sanesoft.hlsserver.service.audio.ffmpeg;

import java.util.List;

public interface FfmpegCommandSupplier {

    List<String> getFfmpegCommands(String pathToInputFile, String pathToOutputFile);
}
