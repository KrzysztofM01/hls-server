package com.sanesoft.hlsserver.mapper;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.database.entity.User;
import com.sanesoft.hlsserver.model.request.UserRequest;
import com.sanesoft.hlsserver.model.response.AudioFileInfoResponse;
import com.sanesoft.hlsserver.model.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDtoMapper implements DtoMapper<User, UserRequest, UserResponse> {

    private final DtoResponseMapper<AudioFileInfo, AudioFileInfoResponse> audioFileMapper;

    public UserDtoMapper(DtoResponseMapper<AudioFileInfo, AudioFileInfoResponse> audioFileMapper) {
        this.audioFileMapper = audioFileMapper;
    }

    @Override
    public User mapRequest(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .build();
    }

    @Override
    public UserResponse mapResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .audioFileInfos(user.getAudioFileInfos()
                        .stream()
                        .map(audioFileMapper::mapResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
