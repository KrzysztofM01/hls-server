package com.sanesoft.hlsserver.service.audio.m3u8.reader;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import com.sanesoft.hlsserver.config.M3U8FileReaderConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GcpM3U8FileReaderTest {

    @Mock
    private M3U8FileReaderConfig config;
    @Mock
    private GcpStorageConfig storageConfig;
    @Mock
    private Storage storage;
    @Mock
    Blob blob;
    @InjectMocks
    private GcpM3U8FileReader reader;

    @Test
    void readEncodedM3U8File_readsFromStorageAndReturnsMappedMetadataFile() {
        // given
        Path pathToFile = Path.of("test");
        URI uri = URI.create("http://localhost:8080");
        String bucketName = "some-bucket";
        when(config.getAudioFilePartURI())
                .thenReturn(uri);
        when(storageConfig.getBucketName())
                .thenReturn(bucketName);
        when(storage.get(bucketName, "test"))
                .thenReturn(blob);
        String contentInStorage = "something;\n" +
                "output1.ts";
        when(blob.getContent())
                .thenReturn(contentInStorage.getBytes(StandardCharsets.UTF_8));

        // when
        var result = reader.readEncodedM3U8File(
                pathToFile, "user", "audio"
        );

        // then
        assertEquals("something;\nhttp://localhost:8080/users/user/audio-files/audio/1", result);
    }
}