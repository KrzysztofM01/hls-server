package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import com.sanesoft.hlsserver.service.audio.exception.M3U8WriterException;
import com.sanesoft.hlsserver.service.audio.storage.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class GcpM3U8FileWriter implements M3U8FileWriter {

    private final Storage storage;
    private final GcpStorageConfig config;

    @Override
    public Path getPathWhereFileShouldBeStored() {
        try {
            return Files.createTempDirectory("m3u8_temp_dir");
        } catch (IOException e) {
            throw new M3U8WriterException("Unable to create temporary directory for audio storage", e);
        }
    }

    @Override
    public Path storeM3U8Files(Path m3u8OutputFile) {
        Path parentAudioDir = m3u8OutputFile.getParent();
        System.out.println(parentAudioDir);
        System.out.println(parentAudioDir.getParent());
        System.out.println(parentAudioDir.getParent().getFileName());
        Path parentGcpPath = config.getCloudStorageDirectory()
                .resolve(parentAudioDir.getParent().getFileName())
                .resolve(parentAudioDir.getFileName());
        try (var stream = Files.newDirectoryStream(parentAudioDir)) {
            for (Path pathInDirectory : stream) {
                storage.create(
                        BlobInfo.newBuilder(
                                config.getBucketName(),
                                parentGcpPath
                                        .resolve(pathInDirectory.getFileName())
                                        .toAbsolutePath()
                                        .toString()
                        ).build(),
                        Files.readAllBytes(pathInDirectory)
                );
                Files.delete(pathInDirectory);
            }
            Files.delete(parentAudioDir);
            return parentGcpPath.resolve("output.m3u8");
        } catch (IOException e) {
            throw new M3U8WriterException("Unable to store files in cloud", e);
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.GCP;
    }
}
