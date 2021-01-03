package com.sanesoft.hlsserver.service.audio;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.service.audio.exception.M3UEncoderException;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class M3U8FFmpeg8Encoder implements M3U8Encoder {

    private final AudioFileConfig config;

    public M3U8FFmpeg8Encoder(AudioFileConfig config) {
        this.config = config;
    }

    @Override
    public Path encodeFileToM3UFormat(User user, String audioName, InputStream fileStream) throws M3UEncoderException {
        try {
            Path pathToFile = config.getRootAudioFileSavePath()
                    .resolve(user.getName())
                    .resolve(audioName);
            Files.createDirectories(pathToFile.getParent());
            //TODO we have to temporarily save that file to disk
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput("E:\\tmp\\testxx\\part1.mp3")
                    .overrideOutputFiles(true)
                    .addOutput("E:\\tmp\\testxx\\java\\output.m3u8")
                    .disableVideo()
                    .setAudioCodec("aac")
                    .setAudioBitRate(128000)
                    .addExtraArgs("-hls_list_size", "0", "-hls_time", "5") // TODO move time to config
                    .done();
            FFmpeg ffmpeg = new FFmpeg(config.getPathToFFmpeg());
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            //TODO after this is done, we have to change the chunks path? or we can also do that in controller - this will allow us to commodate for changes of api address
            executor.createJob(builder).run();
            return pathToFile;
        } catch (IOException e) {
            throw new M3UEncoderException("Error while encoding file", e);
        }
    }
}
