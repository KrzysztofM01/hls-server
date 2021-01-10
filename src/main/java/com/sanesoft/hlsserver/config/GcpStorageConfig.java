package com.sanesoft.hlsserver.config;

import java.nio.file.Path;

public interface GcpStorageConfig {

    String getBucketName();

    Path getCloudStorageDirectory();
}
