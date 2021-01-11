package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Responsible for encoding audio file to m3u8 metadata file and audio chunks.
 *
 * @author kmirocha
 */
public interface M3U8Encoder {

    /**
     * Encodes given audio stream to m3u8 format, splitting audio into configured chunks and creating proper m3u8
     * metadata file.
     *
     * @param userName - name of the user
     * @param audioName - name of the audio
     * @param audioFile - stream from audio file
     * @return path where m3u8 metadata file is stored.
     */
    Path encodeFileToM3UFormat(String userName, String audioName, InputStream audioFile);
}
