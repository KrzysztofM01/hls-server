package com.sanesoft.hlsserver.service.audio.reader;

import com.sanesoft.hlsserver.service.audio.exception.AudioFileReadException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NioAudioFileReaderTest {

    private final AudioFileReader reader = new NioAudioFileReader();

    private Path tempDirPath;
    private Path tempPath;
    String content = "some-content-of-file";

    @BeforeEach
    void init() throws IOException {
        tempDirPath = Files.createTempDirectory("some-temp-dir");
        tempPath = Files.createFile(tempDirPath.resolve("output1.ts"));
        Files.write(tempPath, content.getBytes(StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(tempPath);
        Files.delete(tempDirPath);
    }

    @Test
    void readAudioPartFile_returnsProperResult() {
        // given
        Integer id = 1;

        // when
        var result = reader.readAudioPartFile(tempDirPath, id);

        // then
        assertArrayEquals(content.getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    void readAudioPartFileOfNonExistingId_throwsException() {
        // given
        Integer id = 2;

        // when
        var result = assertThrows(
                AudioFileReadException.class,
                () -> reader.readAudioPartFile(tempDirPath, id)
        );

        // then
        assertNotNull(result);
    }
}