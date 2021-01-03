package com.sanesoft.hlsserver.service.audio;

import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.service.audio.exception.M3UEncoderException;

import java.io.InputStream;
import java.nio.file.Path;

public interface M3U8Encoder {

    Path encodeFileToM3UFormat(User user, String audioName, InputStream audioFile) throws M3UEncoderException;
}
