package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.sanesoft.hlsserver.service.audio.exception.M3U8ReaderException;

import java.nio.file.Path;

public interface M3U8FileReader {

    String readEncodedM3U8File(Path pathToFile, String userName, String audioName) throws M3U8ReaderException;
}
