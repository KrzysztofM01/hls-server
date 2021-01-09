package com.sanesoft.hlsserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AudioFileInfoResponse {

    @NonNull
    String name;
}
