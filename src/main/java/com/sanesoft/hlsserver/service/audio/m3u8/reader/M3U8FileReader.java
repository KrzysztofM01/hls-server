package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.sanesoft.hlsserver.service.audio.storage.StorageTypeBasedInterface;

import java.nio.file.Path;

/**
 * Responsible from retrieving and reading m3u8 metadata file.
 *
 * @author kmirocha
 */
public interface M3U8FileReader extends StorageTypeBasedInterface {

    /**
     * Retrieves and reads m3u8 metadata file from given location.
     *
     * @param pathToFile - path to location of m3u8 file
     * @param userName - name of the user
     * @param audioName - name of the audio
     * @return content of m3u8 metadata file.
     */
    String readEncodedM3U8File(Path pathToFile, String userName, String audioName);
}
