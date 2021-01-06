package com.sanesoft.hlsserver.service.audio.ffmpeg;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrampFfmpegCommandSupplierTest {

    @Mock
    private AudioFileConfig config;
    @InjectMocks
    private BrampFfmpegCommandSupplier ffmpegCommandSupplier;

    @Test
    void getFffmpegCommands_returnsProperlyFormattedListOfCommands() {
        // given
        when(config.getAudioPartDuration())
                .thenReturn(Duration.ofSeconds(5));
        when(config.getAudioBitRate())
                .thenReturn(64000);

        // when
        var result = ffmpegCommandSupplier.getFfmpegCommands("input-path", "output-path");

        // then
        assertEquals(
                List.of("-y", "-v", "error", "-i", "input-path", "-vn", "-acodec", "aac",
                        "-b:a", "64000", "-hls_list_size", "0", "-hls_time", "5", "output-path"),
                result
        );
    }
}