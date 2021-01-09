package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import com.google.common.io.Resources;
import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;
import com.sanesoft.hlsserver.service.audio.ffmpeg.FfmpegCommandSupplier;
import com.sanesoft.hlsserver.service.audio.ffmpeg.FfmpegExecutorWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class M3U8FFmpegEncoderTest {

    @Mock
    private AudioFileConfig config;
    @Mock
    private FfmpegExecutorWrapper ffmpegExecutor;
    @Mock
    private FfmpegCommandSupplier ffmpegCommandSupplier;
    @InjectMocks
    private M3U8FFmpegEncoder encoder;

    private Path tempDirPath;

    @BeforeEach
    void init() throws IOException {
        tempDirPath = Files.createTempDirectory("some-temp-dir");
    }

    @AfterEach
    void tearDown() throws IOException {
        FileSystemUtils.deleteRecursively(tempDirPath);
    }

    @Test
    void encodeFileToM3U8Format_callsUnderlyingInterfaces() throws IOException {
        // given
        List<String> someList = List.of("cmd1", "cmd2");
        Path desiredPath = tempDirPath.resolve("user").resolve("audio").resolve("output.m3u8");
        when(config.getRootAudioFileSavePath())
                .thenReturn(tempDirPath);
        when(ffmpegCommandSupplier.getFfmpegCommands(anyString(), eq(desiredPath.toString())))
                .thenReturn(someList);

        // when
        var result = encoder.encodeFileToM3UFormat("user", "audio",
                Resources.getResource("test_audio.mp3").openStream());

        // then
        verify(ffmpegExecutor, times(1)).execute(someList);
        assertEquals(desiredPath, result);
    }

    @Test
    void encodeM3U8FileWhenFileCannotBeRead_throwsException() throws IOException {
        // given
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read(any()))
                .thenThrow(new IOException());

        // when
        var result = assertThrows(
                M3U8EncoderException.class,
                () -> encoder.encodeFileToM3UFormat("user", "audio", inputStream)
        );

        // then
        assertNotNull(result);
    }
}