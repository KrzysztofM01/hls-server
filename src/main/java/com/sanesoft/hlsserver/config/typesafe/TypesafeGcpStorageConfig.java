package com.sanesoft.hlsserver.config.typesafe;

import com.sanesoft.hlsserver.config.GcpStorageConfig;
import com.typesafe.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class TypesafeGcpStorageConfig implements GcpStorageConfig {

    private static final String BUCKET_NAME = "bucket-name";
    private static final String CLOUD_STORAGE_DIRECTORY = "gcp-storage-directory";

    private final Config config;

    @Override
    public String getBucketName() {
        return config.getString(BUCKET_NAME);
    }

    @Override
    public Path getCloudStorageDirectory() {
        return Path.of(config.getString(CLOUD_STORAGE_DIRECTORY));
    }
}
