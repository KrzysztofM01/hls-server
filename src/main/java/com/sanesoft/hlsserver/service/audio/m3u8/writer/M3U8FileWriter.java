package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import java.nio.file.Path;

public interface M3U8FileWriter {

    Path getPathWhereFileShouldBeStored();

    void storeM3U8Files(Path m3u8ParentDirectory);
}
