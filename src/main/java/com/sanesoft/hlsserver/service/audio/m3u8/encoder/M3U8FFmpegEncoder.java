package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;
import com.sanesoft.hlsserver.service.audio.ffmpeg.FfmpegCommandSupplier;
import com.sanesoft.hlsserver.service.audio.ffmpeg.FfmpegExecutorWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class M3U8FFmpegEncoder implements M3U8Encoder {

    private final AudioFileConfig config;
    private final FfmpegExecutorWrapper ffmpegExecutor;
    private final FfmpegCommandSupplier ffmpegCommandSupplier;

    @Override
    public Path encodeFileToM3UFormat(String userName, String audioName, InputStream fileStream) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("audioForFfmpeg", ".extension");
            FileUtils.copyInputStreamToFile(fileStream, tempFile);

            Path pathToM3U8File = config.getRootAudioFileSavePath()
                    .resolve(userName)
                    .resolve(audioName)
                    .resolve("output.m3u8");
            Files.createDirectories(pathToM3U8File.getParent());

            List<String> ffmpegCmds = ffmpegCommandSupplier.getFfmpegCommands(
                    tempFile.getAbsolutePath(), pathToM3U8File.toString()
            );
            ffmpegExecutor.execute(ffmpegCmds);
            return pathToM3U8File;
        } catch (IOException e) {
            throw new M3U8EncoderException("Error while encoding file", e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }
}
