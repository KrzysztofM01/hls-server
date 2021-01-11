package com.sanesoft.hlsserver.model.response;

import com.sanesoft.hlsserver.database.entity.AudioFileInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * POJO representation of response containing info from {@link AudioFileInfo}.
 *
 * @author kmirocha
 */
@Value
@Builder
@AllArgsConstructor
public class AudioFileInfoResponse {

    @NonNull
    String name;
}
