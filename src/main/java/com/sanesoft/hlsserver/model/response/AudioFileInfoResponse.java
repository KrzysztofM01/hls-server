package com.sanesoft.hlsserver.model.response;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AudioFileInfoResponse {

    @NonNull
    String name;
}
