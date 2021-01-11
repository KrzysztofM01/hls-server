package com.sanesoft.hlsserver.service.audio;

import java.io.InputStream;

/**
 * Service responsible for handling all requests concerning audio files.
 *
 * @author kmirocha
 */
public interface AudioFileService {

    /**
     * Saves given audio file into server's storage.
     *
     * @param userName - name of the user
     * @param audioName - name of the audio
     * @param inputStream - input stream of audio
     */
    void saveAudioFile(String userName, String audioName, InputStream inputStream);

    /**
     * Retrieves metadata information of request audio file.
     *
     * @param userName - name of the user
     * @param audioName - name of the audio
     * @return metadata of audio file as string
     */
    String getAudioFileMetadata(String userName, String audioName);

    /**
     * Retrieves audio chunk contents from server's storage.
     *
     * @param userName - name of user
     * @param audioName - name of audio
     * @param audioPartId - id of audio chunk
     * @return audio chunk contents in byte array
     */
    byte[] getAudioFile(String userName, String audioName, Integer audioPartId);
}
