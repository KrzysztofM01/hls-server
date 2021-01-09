package com.sanesoft.hlsserver.mapper;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.AudioFileInfoResponse;
import com.sanesoft.hlsserver.model.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserDtoMapperTest {

    @Mock
    private DtoResponseMapper<AudioFileInfo, AudioFileInfoResponse> audioDtoMapper;
    @InjectMocks
    private UserDtoMapper dtoMapper;

    private final AudioFileInfo info1 = AudioFileInfo.builder()
            .name("test1")
            .pathToFile(Path.of("some-path-1"))
            .build();
    private final AudioFileInfo info2 = AudioFileInfo.builder()
            .name("test2")
            .pathToFile(Path.of("some-path-2"))
            .build();

    private final AudioFileInfoResponse infoResponse1 = new AudioFileInfoResponse("test1");
    private final AudioFileInfoResponse infoResponse2 = new AudioFileInfoResponse("test2");

    @Test
    void mapToResponse_returnsProperResult() {
        // given
        Mockito.when(audioDtoMapper.mapToResponse(info1))
                .thenReturn(infoResponse1);
        Mockito.when(audioDtoMapper.mapToResponse(info2))
                .thenReturn(infoResponse2);

        // when
        var result = dtoMapper.mapToResponse(User.builder()
                .name("test")
                .audioFileInfos(List.of(info1, info2))
                .build());

        // then
        assertEquals(UserResponse.builder()
                .name("test")
                .audioFileInfos(List.of(infoResponse1, infoResponse2))
                .build(), result);
    }

    @Test
    void mapFromRequest_returnsProperResult() {
        // when
        var result = dtoMapper.mapFromRequest(UserRequest.builder()
                .name("test")
                .build());

        // then
        assertEquals(User.builder()
                .name("test")
                .build(), result);
    }
}