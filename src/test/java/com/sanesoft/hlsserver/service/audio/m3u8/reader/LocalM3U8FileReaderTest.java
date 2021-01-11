package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.google.common.io.Resources;
import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8ReaderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalM3U8FileReaderTest {

    @Mock
    private M3U8FileReaderConfig config;
    @InjectMocks
    private LocalM3U8FileReader reader;

    @Test
    void readEncodedM3U8File_returnsProperResult() throws URISyntaxException {
        // given
        URI uri = URI.create("http://localhost:8080");
        Mockito.when(config.getAudioFilePartURI())
                .thenReturn(uri);
        Path path = Paths.get(Resources.getResource("fileReaderTestFile.m3u8").toURI());

        // when
        var result = reader.readEncodedM3U8File(path, "test", "some-audio");

        // then
        assertEquals(
                "#EXTM3U\n" +
                        "#EXT-X-VERSION:3\n" +
                        "#EXT-X-TARGETDURATION:5\n" +
                        "#EXT-X-MEDIA-SEQUENCE:0\n" +
                        "#EXTINF:5.015511,\n" +
                        "http://localhost:8080/users/test/audio-files/some-audio/0\n" +
                        "#EXTINF:4.992289,\n" +
                        "http://localhost:8080/users/test/audio-files/some-audio/1\n" +
                        "#EXTINF:4.992289,\n" +
                        "http://localhost:8080/users/test/audio-files/some-audio/2\n" +
                        "#EXT-X-ENDLIST"
                , result);
    }

    @Test
    void readEncodedM3U8FileWhenFileDoestNotExist_throwsException() {
        // given
        Path path = Path.of("/some/nonexisting/path");

        // when
        var result = assertThrows(
                M3U8ReaderException.class,
                () -> reader.readEncodedM3U8File(path, "test", "some-audio")
        );

        // then
        assertNotNull(result);
    }
}