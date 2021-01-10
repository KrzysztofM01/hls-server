package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.sanesoft.hlsserver.service.audio.storage.StorageTypeBasedInterface;

import java.nio.file.Path;

public interface M3U8FileReader extends StorageTypeBasedInterface {

    String readEncodedM3U8File(Path pathToFile, String userName, String audioName);
}
