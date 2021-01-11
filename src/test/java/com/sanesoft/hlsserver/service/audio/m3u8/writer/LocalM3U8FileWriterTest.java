package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalM3U8FileWriterTest {

    @Mock
    private AudioFileConfig config;
    @InjectMocks
    private LocalM3U8FileWriter writer;

    private final Path path = Path.of("/some/path");

    @Test
    void getPathWhereFileShouldBeStored_shouldReturnPathFromConfig() throws IOException {
        // given
        when(config.getRootAudioFileSavePath())
                .thenReturn(path);

        // when
        var result = writer.getPathWhereFileShouldBeStored();

        // then
        assertEquals(path, result);
    }

    @Test
    void storeM3U8Files_shouldReturnInputPath() throws IOException {
        // when
        var result = writer.storeM3U8Files(path);

        // then
        assertEquals(path, result);
    }
}