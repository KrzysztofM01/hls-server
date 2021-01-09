package com.sanesoft.hlsserver.config.typesafe;

import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypesafeM3U8FileReaderTest {

    private final Config c = ConfigFactory.parseString("audio-file-part-uri=\"http://localhost:8080\"");
    private final M3U8FileReaderConfig config = new TypesafeM3U8FileReaderConfig(c);

    @Test
    void getAudioFilePartUri_returnsProperURI() {
        // when
        URI result = config.getAudioFilePartURI();

        // then
        assertEquals(URI.create("http://localhost:8080"), result);
    }
}