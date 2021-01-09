package com.sanesoft.hlsserver.service.audio;

import java.io.InputStream;

public interface AudioFileService {

    void saveAudioFile(String userName, String audioName, InputStream inputStream);

    String getAudioFileIndex(String userName, String audioName);

    byte[] getAudioFile(String userName, String audioName, Integer audioPartId);
}
