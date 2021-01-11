package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import com.google.common.io.Resources;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;
import com.sanesoft.hlsserver.service.audio.ffmpeg.FfmpegCommandSupplier;
import com.sanesoft.hlsserver.service.audio.ffmpeg.FfmpegExecutorWrapper;
import com.sanesoft.hlsserver.service.audio.m3u8.writer.M3U8FileWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class M3U8FFmpegEncoderTest {

    @Mock
    private M3U8FileWriter writer;
    @Mock
    private FfmpegExecutorWrapper ffmpegExecutor;
    @Mock
    private FfmpegCommandSupplier ffmpegCommandSupplier;
    @InjectMocks
    private M3U8FFmpegEncoder encoder;

    private final Path tempDirPath = Path.of("/some/path");

    @Test
    void encodeFileToM3U8Format_callsUnderlyingInterfaces() throws IOException {
        // given
        List<String> someList = List.of("cmd1", "cmd2");
        Path desiredPath = tempDirPath.resolve("user").resolve("audio").resolve("output.m3u8");
        when(writer.getPathWhereFileShouldBeStored())
                .thenReturn(tempDirPath);
        when(ffmpegCommandSupplier.getFfmpegCommands(anyString(), eq(desiredPath.toString())))
                .thenReturn(someList);
        when(writer.storeM3U8Files(desiredPath))
                .thenReturn(desiredPath);

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