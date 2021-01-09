package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import java.io.InputStream;
import java.nio.file.Path;

public interface M3U8Encoder {

    Path encodeFileToM3UFormat(String userName, String audioName, InputStream audioFile);
}
