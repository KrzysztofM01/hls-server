package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import com.sanesoft.hlsserver.database.entity.User;

import java.io.InputStream;
import java.nio.file.Path;

public interface M3U8Encoder {

    Path encodeFileToM3UFormat(User user, String audioName, InputStream audioFile);
}
