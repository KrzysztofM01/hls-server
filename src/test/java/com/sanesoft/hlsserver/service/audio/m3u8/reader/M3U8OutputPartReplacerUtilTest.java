package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class M3U8OutputPartReplacerUtilTest {

    private final URI uri = URI.create("http://localhost:8080");
    private final String userName = "some-user";
    private final String audioName = "some-audio";

    @Test
    void replaceOutputPart_returnsProperResultForNonOutput() throws URISyntaxException {
        // given
        String input = "test, something not related";

        // when
        var result = M3U8OutputPartReplacerUtil.replaceOutputPart(
                input, uri, userName, audioName
        );

        // then
        assertEquals(input, result);
    }

    @Test
    void replaceOutputPart_returnsProperResultForOutput() {
        // given
        String input = "output23.ts";

        // when
        var result = M3U8OutputPartReplacerUtil.replaceOutputPart(
                input, uri, userName, audioName
        );

        // then
        assertEquals("http://localhost:8080/users/some-user/audio-files/some-audio/23", result);
    }
}