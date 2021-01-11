package com.sanesoft.hlsserver.service.audio.storage;

/**
 * Enum type describing types of available storages in hls-server.
 * <ul>
 *     <li>LOCAL - reads/stores all files locally on server</li>
 *     <li>GCP - reads/stores all files on google storage</li>
 * </ul>
 *
 * @author kmirocha
 */
public enum StorageType {
    LOCAL, GCP
}
