package com.sanesoft.hlsserver.config;

import java.nio.file.Path;

/**
 * Responsible for configuration of gcp.
 *
 * @author kmirocha
 */
public interface GcpStorageConfig {

    /**
     * @return bucket name of gcp where audio files will be stored.
     */
    String getBucketName();

    /**
     * @return root path under which files will be stored in gcp bucket.
     */
    Path getCloudStorageDirectory();
}
