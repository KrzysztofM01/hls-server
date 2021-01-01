package com.sanesoft.hlsserver.mapper;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.model.response.AudioFileInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class AudioFileDtoMapper implements DtoResponseMapper<AudioFileInfo, AudioFileInfoResponse>{

    @Override
    public AudioFileInfoResponse mapResponse(AudioFileInfo audioFileInfo) {
        return AudioFileInfoResponse.builder()
                .name(audioFileInfo.getName())
                .build();
    }
}
