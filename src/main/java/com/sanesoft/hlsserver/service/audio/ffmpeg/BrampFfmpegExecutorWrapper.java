package com.sanesoft.hlsserver.service.audio.ffmpeg;

import com.sanesoft.hlsserver.config.AudioFileConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8EncoderException;
import net.bramp.ffmpeg.FFmpeg;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link FfmpegExecutorWrapper} based on bramp ffmpeg command wrapper.
 *
 * @author kmirocha
 */
@Service
public class BrampFfmpegExecutorWrapper implements FfmpegExecutorWrapper {

    private final FFmpeg ffmpeg;

    public BrampFfmpegExecutorWrapper(AudioFileConfig config) {
        try {
            this.ffmpeg = new FFmpeg(config.getPathToFFmpeg());
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create ffmpeg instance due to", e);
        }
    }

    @Override
    public void execute(List<String> args) {
        try {
            // TODO gotta make it non blocking - the downside then is that we can return OK whereas this actually failed
            ffmpeg.run(args);
        } catch (IOException e) {
            throw new M3U8EncoderException("Error while encoding file", e);
        }
    }
}
