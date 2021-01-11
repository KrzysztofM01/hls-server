package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.sanesoft.hlsserver.service.audio.storage.StorageTypeBasedInterface;

import java.nio.file.Path;

/**
 * Responsible for writing m3u8 metadata file along with audio chunks into implemented storage.
 *
 * @author kmirocha
 */
public interface M3U8FileWriter extends StorageTypeBasedInterface {

    /**
     * @return path where initial output files from m3u8 encoding should be stored.
     */
    Path getPathWhereFileShouldBeStored();

    /**
     * Stores supplied m3u8 metadata file and audio chunks into designed location, based on implementation.
     *
     * @param m3u8OutputFile - path to m3u8 metadata file
     * @return path where m3u8 metadata was finally stored.
     */
    Path storeM3U8Files(Path m3u8OutputFile);
}
