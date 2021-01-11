package com.sanesoft.hlsserver.service.audio.m3u8.writer;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.sanesoft.hlsserver.config.GcpStorageConfig;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GcpM3U8FileWriterTest {

    @Mock
    private Storage storage;
    @Mock
    private GcpStorageConfig config;
    @InjectMocks
    private GcpM3U8FileWriter writer;

    @Test
    void getPathWhereFileShouldBeStored_returnsProperPath() {
        // when
        var result = writer.getPathWhereFileShouldBeStored();

        // then
        assertThat(result.getFileName().toString(), containsString("m3u8_temp_dir"));
    }

    @Test
    void storeM3U8Files_callsStorageForAllFilesInDirAndCleansFiles() throws IOException {
        // given
        Path tmpParentPath = Files.createTempDirectory("someTmpPath");
        Path dirPath = tmpParentPath.resolve("some-user").resolve("some-audio");
        Files.createDirectories(dirPath);
        Path filePath1 = Files.createFile(dirPath.resolve("file1"));
        byte[] content1 = {0, 1, 2};
        Files.write(filePath1, content1);
        Path filePath2 = Files.createFile(dirPath.resolve("file2"));
        byte[] content2 = {3, 4, 5};
        Files.write(filePath2, content2);
        String bucketName = "some-bucket";
        Path pathToCloudStorage = Path.of("C:\\some\\cloud\\path");
        when(config.getBucketName())
                .thenReturn(bucketName);
        when(config.getCloudStorageDirectory())
                .thenReturn(pathToCloudStorage);

        // when
        var result = writer.storeM3U8Files(filePath1);

        // then
        verify(storage, atLeastOnce()).create(
                BlobInfo.newBuilder(bucketName, pathToCloudStorage.resolve("some-user").resolve("some-audio").resolve("file1").toString()).build(),
                content1
        );
        verify(storage, atLeastOnce()).create(
                BlobInfo.newBuilder(bucketName, pathToCloudStorage.resolve("some-user").resolve("some-audio").resolve("file2").toString()).build(),
                content2
        );
        assert Files.list(tmpParentPath.resolve("some-user")).findFirst().isEmpty();
        assertEquals(pathToCloudStorage.resolve("some-user").resolve("some-audio").resolve("output.m3u8"), result);

        //cleanup
        FileUtils.deleteDirectory(tmpParentPath.toFile());
    }
}