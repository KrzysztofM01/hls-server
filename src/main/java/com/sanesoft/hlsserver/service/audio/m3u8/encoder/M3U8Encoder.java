package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;

import java.io.InputStream;
import java.nio.file.Path;

public interface M3U8Encoder {

    Path encodeFileToM3UFormat(User user, String audioName, InputStream audioFile) throws M3U8EncoderException;
}
