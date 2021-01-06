package com.sanesoft.hlsserver.mapper;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.model.response.AudioFileInfoResponse;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioFileDtoMapperTest {

    private final AudioFileDtoMapper dtoMapper = new AudioFileDtoMapper();

    @Test
    void mapToResponse_returnsProperResult() {
        // when
        AudioFileInfoResponse result = dtoMapper.mapToResponse(AudioFileInfo.builder()
                .name("test")
                .pathToFile(Path.of("some-path"))
                .build());

        // then
        assertEquals(new AudioFileInfoResponse("test"), result);
    }
}