package com.sanesoft.hlsserver.service.audio.reader;

import java.nio.file.Path;

public interface AudioFileReader {

    byte[] readAudioPartFile(Path pathToAudioRootDirectory, Integer audioPathId);
}
