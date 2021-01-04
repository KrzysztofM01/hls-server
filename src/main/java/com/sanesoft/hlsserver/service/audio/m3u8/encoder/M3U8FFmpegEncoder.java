package com.sanesoft.hlsserver.service.audio.m3u8.encoder;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class M3U8FFmpegEncoder implements M3U8Encoder {

    private final AudioFileConfig config;
    private final FFmpegExecutor ffmpegExecutor;

    public M3U8FFmpegEncoder(AudioFileConfig config) {
        try {
            FFmpeg ffmpeg = new FFmpeg(config.getPathToFFmpeg());
            this.ffmpegExecutor = new FFmpegExecutor(ffmpeg);
            this.config = config;
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create ffmpeg instance due to", e);
        }
    }

    @Override
    public Path encodeFileToM3UFormat(User user, String audioName, InputStream fileStream) throws M3U8EncoderException {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("audioForFfmpeg", ".extension");
            FileUtils.copyInputStreamToFile(fileStream, tempFile);

            Path pathToM3U8File = config.getRootAudioFileSavePath()
                    .resolve(user.getName())
                    .resolve(audioName)
                    .resolve("output.m3u8");
            Files.createDirectories(pathToM3U8File.getParent());

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(tempFile.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(pathToM3U8File.toString())
                    .disableVideo()
                    .setAudioCodec("aac")
                    .setAudioBitRate(config.getAudioBitRate()) // 128 000
                    .addExtraArgs(
                            "-hls_list_size", "0",
                            "-hls_time", String.valueOf(config.getAudioPartDuration().toSeconds())
                    )
                    .done();

            ffmpegExecutor.createJob(builder).run(); // TODO gotta make it non blocking
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
