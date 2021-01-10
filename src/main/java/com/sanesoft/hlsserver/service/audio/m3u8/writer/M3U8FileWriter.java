package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.sanesoft.hlsserver.service.audio.storage.StorageTypeBasedInterface;

import java.nio.file.Path;

public interface M3U8FileWriter extends StorageTypeBasedInterface {

    Path getPathWhereFileShouldBeStored();

    Path storeM3U8Files(Path m3u8OutputFile);
}
