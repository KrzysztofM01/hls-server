package com.sanesoft.hlsserver.service.audio.storage;

/**
 * Interface which denotes what king of {@link StorageType} is given service implementation, allowing the server
 * to properly annotate beans to be selected later in {@link StorageTypeConfiguration}.
 *
 * @author kmirocha
 */
public interface StorageTypeBasedInterface {

    /**
     * @return what {@link StorageType} given implementation is using.
     */
    StorageType getStorageType();
}
