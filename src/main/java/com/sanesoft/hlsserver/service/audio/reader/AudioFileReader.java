package com.sanesoft.hlsserver.service.audio.reader;

import com.sanesoft.hlsserver.service.audio.storage.StorageTypeBasedInterface;

import java.nio.file.Path;

/**
 * Responsible for reading audio file from storage.
 *
 * @author kmirocha
 */
public interface AudioFileReader extends StorageTypeBasedInterface {

    /**
     * Reads audio file chunk content of given id from given path.
     *
     * @param pathToAudioRootDirectory - path where audio file chunks are stored
     * @param audioPathId - id of audio chunk
     * @return byte array containing audio file chunk contents.
     */
    byte[] readAudioPartFile(Path pathToAudioRootDirectory, Integer audioPathId);
}
