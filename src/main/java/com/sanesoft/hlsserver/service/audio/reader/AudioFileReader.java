package com.sanesoft.hlsserver.service.audio.reader;

import com.sanesoft.hlsserver.service.audio.storage.StorageTypeBasedInterface;

import java.nio.file.Path;

public interface AudioFileReader extends StorageTypeBasedInterface {

    byte[] readAudioPartFile(Path pathToAudioRootDirectory, Integer audioPathId);
}
