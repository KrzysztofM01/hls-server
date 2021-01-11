package com.sanesoft.hlsserver.mapper;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import com.sanesoft.hlsserver.model.response.AudioFileInfoResponse;
import org.springframework.stereotype.Service;

/**
 * Responsible for mapping database entity {@link AudioFileInfo} to rest response {@link AudioFileInfoResponse}.
 *
 * @author kmirocha
 */
@Service
public class AudioFileDtoMapper implements DtoResponseMapper<AudioFileInfo, AudioFileInfoResponse>{

    @Override
    public AudioFileInfoResponse mapToResponse(AudioFileInfo audioFileInfo) {
        return AudioFileInfoResponse.builder()
                .name(audioFileInfo.getName())
                .build();
    }
}
