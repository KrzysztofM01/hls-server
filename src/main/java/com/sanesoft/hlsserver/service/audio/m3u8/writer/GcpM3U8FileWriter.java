package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.AudioFileConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class GcpM3U8FileWriter implements M3U8FileWriter {

    private final Storage storage;
    private final AudioFileConfig config;

    // TODO config:
    //  -

    @Override
    public Path getPathWhereFileShouldBeStored() {
        try {
            return Files.createTempDirectory("saas"); //TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void storeM3U8Files(Path m3u8ParentDirectory) {
        // TODO for each file in that directory we gotta save it
        // TODO some config based factory which will choose proper implementation of this class
        try {
            Files.newDirectoryStream(m3u8ParentDirectory)
                    .forEach(path -> {
                        try {
                            try {
                                storage.create(
                                        BlobInfo.newBuilder(
                                                "hls-server-bucket",
                                                config.getRootAudioFileSavePath().resolve(path.getFileName()).toString() // TODO fix
                                                ).build(),
                                        Files.readAllBytes(path)
                                        );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } finally {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(m3u8ParentDirectory); // TODO if ffmpeg throws error this wont be done, maybe expose new method cleanStuff for this interface?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
