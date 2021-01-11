package com.sanesoft.hlsserver.service.audio.reader;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GcpAudioFileReaderTest {

    @Mock
    private Storage storage;
    @Mock
    private GcpStorageConfig config;
    @Mock
    Blob blob;
    @InjectMocks
    private GcpAudioFileReader reader;

    @Test
    void readAudioPartFile_callsUnderlyingInterfaces() {
        // given
        Integer audioId = 1;
        Path somePath = Path.of("/some/path");
        String bucketName = "some-bucket";
        byte[] expected = {0, 1, 2, 3};
        when(config.getBucketName())
                .thenReturn(bucketName);
        when(storage.get(bucketName, somePath.resolve("output1.ts").toString()))
                .thenReturn(blob);
        when(blob.getContent())
                .thenReturn(expected);

        // when
        var result = reader.readAudioPartFile(somePath, audioId);

        // then
        assertEquals(expected, result);
    }
}