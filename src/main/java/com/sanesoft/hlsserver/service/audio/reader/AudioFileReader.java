package com.sanesoft.hlsserver.service.audio.reader;

import com.sanesoft.hlsserver.service.audio.exception.AudioFileReadException;

import java.nio.file.Path;

public interface AudioFileReader {

    byte[] readAudioPartFile(Path pathToAudioRootDirectory, Integer audioPathId) throws AudioFileReadException;
}
