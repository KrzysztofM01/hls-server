package com.sanesoft.hlsserver.service.audio.ffmpeg;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrampFfmpegCommandSupplier implements FfmpegCommandSupplier {

    private final AudioFileConfig config;

    @Override
    public List<String> getFfmpegCommands(String pathToInputFile, String pathToOutputFile) {
        return new FFmpegBuilder()
                .setInput(pathToInputFile)
                .overrideOutputFiles(true)
                .addOutput(pathToOutputFile)
                .disableVideo()
                .setAudioCodec("aac")
                .setAudioBitRate(config.getAudioBitRate())
                .addExtraArgs(
                        "-hls_list_size", "0",
                        "-hls_time", String.valueOf(config.getAudioPartDuration().toSeconds())
                )
                .done()
                .build();
    }
}
