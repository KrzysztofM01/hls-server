package com.sanesoft.hlsserver.config;

import java.net.URI;

/**
 * Responsible for configuration of m3u8 metadata file readers.
 *
 * @author kmirocha
 */
public interface M3U8FileReaderConfig {

    /**
     * @return URI path where audio chunks will be located in.
     */
    URI getAudioFilePartURI();
}
